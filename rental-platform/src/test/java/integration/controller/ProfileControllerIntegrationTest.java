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
import org.springframework.transaction.annotation.Transactional;
import senla.Main;
import senla.dto.ProfileDto;
import senla.dto.UserDto;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class ProfileControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;
    private ProfileDto profileDto;
    private UserDto userDto;
    private String jwtToken;
    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/profiles";
        profileDto = new ProfileDto();
        profileDto.setUserId(1);
        profileDto.setLastname("testUser");
        profileDto.setFirstname("testUser");
        profileDto.setPhone("12345678");
        profileDto.setEmail("test@example.com");

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

    private ResponseEntity<ProfileDto> createProfile() {
        restTemplate.exchange(baseUrl + "/1", HttpMethod.DELETE, new HttpEntity<>(headers), String.class);
        return restTemplate.exchange(baseUrl, HttpMethod.POST, new HttpEntity<>(profileDto, headers), ProfileDto.class);
    }

    @Test
    void testCreateProfile() {
        ResponseEntity<ProfileDto> response = createProfile();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
    }

    @Test
    void testGetAllProfiles() {
        createProfile();

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(((List<?>) response.getBody()).size()).isGreaterThan(0);
    }

    @Test
    void testGetProfileById() {
        ResponseEntity<ProfileDto> createResponse = createProfile();
        Integer createdId = Objects.requireNonNull(createResponse.getBody()).getId();

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<ProfileDto> getResponse = restTemplate.exchange(baseUrl + "/" + createdId, HttpMethod.GET, entity, ProfileDto.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getId()).isEqualTo(createdId);
    }

    @Test
    void testUpdateProfile() {
        ResponseEntity<ProfileDto> createResponse = createProfile();
        Integer createdId = Objects.requireNonNull(createResponse.getBody()).getId();

        profileDto.setLastname("updatedUser");

        HttpEntity<ProfileDto> entity = new HttpEntity<>(profileDto, headers);
        ResponseEntity<String> updateResponse = restTemplate.exchange(baseUrl + "/" + createdId, HttpMethod.PUT, entity, String.class);

        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isEqualTo("Профиль успешно обновлен");
    }

    @Test
    void testDeleteProfile() {
        ResponseEntity<ProfileDto> createResponse = createProfile();
        Integer createdId = Objects.requireNonNull(createResponse.getBody()).getId();

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<String> deleteResponse = restTemplate.exchange(baseUrl + "/" + createdId, HttpMethod.DELETE, entity, String.class);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deleteResponse.getBody()).isEqualTo("Профиль успешно удален");
    }
}
