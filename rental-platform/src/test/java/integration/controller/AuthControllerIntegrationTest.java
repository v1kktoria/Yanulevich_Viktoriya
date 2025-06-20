package integration.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import senla.Main;
import senla.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Slf4j
public class AuthControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/auth";
        userDto = new UserDto();
    }

    @Test
    void testSignUp() {
        userDto.setUsername("user");
        userDto.setPassword("password");
        HttpEntity<UserDto> requestEntity = new HttpEntity<>(userDto);
        ResponseEntity<UserDto> response = restTemplate.exchange(baseUrl + "/sign-up", HttpMethod.POST, requestEntity, UserDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo(userDto.getUsername());
    }

    @Test
    void testSignIn() {
        userDto.setUsername("user1");
        userDto.setPassword("password1");

        HttpEntity<UserDto> signInRequest = new HttpEntity<>(userDto);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/sign-in", HttpMethod.POST, signInRequest, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains(".");
    }
}
