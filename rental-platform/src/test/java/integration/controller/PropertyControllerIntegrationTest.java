package integration.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import senla.Main;
import senla.dto.PropertyDto;
import senla.model.constant.PropertyType;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PropertyControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    private PropertyDto propertyDto;

    private UserDto userDto;

    private String jwtToken;

    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/properties";
        propertyDto = new PropertyDto();
        propertyDto.setOwnerId(1);
        propertyDto.setType(PropertyType.OFFICE);
        propertyDto.setArea(75.5);
        propertyDto.setPrice(150000.0);
        propertyDto.setRooms(3);
        propertyDto.setDescription("Test Property");

        userDto = new UserDto();
        userDto.setUsername("user1");
        userDto.setPassword("password1");

        jwtToken = loginAndGetJwtToken(userDto);

        headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
    }

    private String loginAndGetJwtToken(UserDto userDto) {
        String loginUrl = "http://localhost:" + port + "/auth/sign-in";
        ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, new HttpEntity<>(userDto), String.class);

        return response.getBody();
    }

    @Test
    void testCreateProperty() {
        HttpEntity<PropertyDto> entity = new HttpEntity<>(propertyDto, headers);
        ResponseEntity<PropertyDto> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, PropertyDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
    }

    @Test
    void testGetPropertyById() {
        ResponseEntity<PropertyDto> createResponse = createProperty();
        Integer createdId = Objects.requireNonNull(createResponse.getBody()).getId();

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<PropertyDto> getResponse = restTemplate.exchange(baseUrl + "/" + createdId, HttpMethod.GET, entity, PropertyDto.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getId()).isEqualTo(createdId);
    }

    @Test
    void testUpdateProperty() {
        ResponseEntity<PropertyDto> createResponse = createProperty();
        Integer createdId = Objects.requireNonNull(createResponse.getBody()).getId();

        propertyDto.setDescription("Updated Description");
        HttpEntity<PropertyDto> entity = new HttpEntity<>(propertyDto, headers);
        ResponseEntity<String> updateResponse = restTemplate.exchange(baseUrl + "/" + createdId, HttpMethod.PUT, entity, String.class);

        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isEqualTo("Недвижимость успешно обновлена");
    }

    @Test
    void testDeleteProperty() {
        ResponseEntity<PropertyDto> createResponse = createProperty();
        Integer createdId = Objects.requireNonNull(createResponse.getBody()).getId();

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<String> deleteResponse = restTemplate.exchange(baseUrl + "/" + createdId, HttpMethod.DELETE, entity, String.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deleteResponse.getBody()).isEqualTo("Недвижимость успешно удалена");
    }

    @Test
    void testSearchProperties() {
        createProperty();

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<List> searchResponse = restTemplate.exchange(
                baseUrl + "/search?type=OFFICE&minPrice=100000&maxPrice=200000&minRooms=2&maxRooms=5&description=Test",
                HttpMethod.GET, entity, List.class);

        assertThat(searchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(searchResponse.getBody()).isNotEmpty();
    }

    private ResponseEntity<PropertyDto> createProperty() {
        HttpEntity<PropertyDto> entity = new HttpEntity<>(propertyDto, headers);
        return restTemplate.exchange(baseUrl, HttpMethod.POST, entity, PropertyDto.class);
    }
}
