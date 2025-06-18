package integration.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import senla.Main;
import senla.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FavoriteControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    private UserDto userDto;

    private String jwtToken;

    private HttpHeaders headers;

    private Integer propertyId = 1;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/favorites";
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
    void testAddToFavorites() {
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        String addUrl = baseUrl + "/" + 1 + "/" + propertyId;

        ResponseEntity<String> response = restTemplate.exchange(addUrl, HttpMethod.POST, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Недвижимость добавлена в избранное");
    }

    @Test
    void testRemoveFromFavorites() {
        testAddToFavorites();

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        String removeUrl = baseUrl + "/" + 1 + "/" + propertyId;

        ResponseEntity<String> response = restTemplate.exchange(removeUrl, HttpMethod.DELETE, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Недвижимость удалена из избранного");
    }
}