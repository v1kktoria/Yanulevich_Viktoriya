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
import senla.dto.AddressDto;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AddressControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    private AddressDto addressDto;

    private UserDto userDto;

    private String jwtToken;

    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/addresses";
        addressDto = new AddressDto();
        addressDto.setPropertyId(1);
        addressDto.setStreet("Test Street");
        addressDto.setCity("Test City");
        addressDto.setCountry("Test Country");
        addressDto.setHouseNumber("Test House Number");

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

    private ResponseEntity<AddressDto> createAddress() {
        return restTemplate.exchange(baseUrl, HttpMethod.POST, new HttpEntity<>(addressDto, headers), AddressDto.class);
    }

    @Test
    void testCreateAddress() {
        HttpEntity<AddressDto> entity = new HttpEntity<>(addressDto, headers);
        ResponseEntity<AddressDto> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, AddressDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
    }

    @Test
    void testGetAddressById() {
        ResponseEntity<AddressDto> createResponse = createAddress();
        Integer createdId = Objects.requireNonNull(createResponse.getBody()).getId();

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<AddressDto> getResponse = restTemplate.exchange(baseUrl + "/" + createdId, HttpMethod.GET, entity, AddressDto.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getId()).isEqualTo(createdId);
    }

    @Test
    void testUpdateAddress() {
        ResponseEntity<AddressDto> createResponse = createAddress();
        Integer createdId = Objects.requireNonNull(createResponse.getBody()).getId();

        addressDto.setStreet("Updated Street");
        HttpEntity<AddressDto> entity = new HttpEntity<>(addressDto, headers);
        ResponseEntity<String> updateResponse = restTemplate.exchange(baseUrl + "/" + createdId, HttpMethod.PUT, entity, String.class);

        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isEqualTo("Адрес успешно обновлен");
    }

    @Test
    void testDeleteAddress() {
        ResponseEntity<AddressDto> createResponse = createAddress();
        Integer createdId = Objects.requireNonNull(createResponse.getBody()).getId();

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<String> deleteResponse = restTemplate.exchange(baseUrl + "/" + createdId, HttpMethod.DELETE, entity, String.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deleteResponse.getBody()).isEqualTo("Адрес успешно удален");
    }

}