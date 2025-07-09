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
import senla.dto.ApplicationDto;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ApplicationControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;
    private ApplicationDto applicationDto;
    private UserDto userDto;
    private String jwtToken;
    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/applications";
        applicationDto = new ApplicationDto();
        applicationDto.setMessage("Test application");
        applicationDto.setOwnerId(1);
        applicationDto.setTenantId(2);
        applicationDto.setPropertyId(1);

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

    private ResponseEntity<ApplicationDto> createApplication() {
        return restTemplate.exchange(baseUrl, HttpMethod.POST, new HttpEntity<>(applicationDto, headers), ApplicationDto.class);
    }

    @Test
    void testCreateApplication() {
        HttpEntity<ApplicationDto> entity = new HttpEntity<>(applicationDto, headers);
        ResponseEntity<ApplicationDto> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, ApplicationDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
    }

    @Test
    void testGetAllApplications() {
        createApplication();

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(((List<?>) response.getBody()).size()).isGreaterThan(0);
    }

    @Test
    void testGetApplicationById() {
        ResponseEntity<ApplicationDto> createResponse = createApplication();
        Integer createdId = Objects.requireNonNull(createResponse.getBody()).getId();

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<ApplicationDto> getResponse = restTemplate.exchange(baseUrl + "/" + createdId, HttpMethod.GET, entity, ApplicationDto.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getId()).isEqualTo(createdId);
    }

    @Test
    void testAcceptApplication() {
        ResponseEntity<ApplicationDto> createResponse = createApplication();
        Integer createdId = Objects.requireNonNull(createResponse.getBody()).getId();

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<String> acceptResponse = restTemplate.exchange(baseUrl + "/" + createdId + "/accept", HttpMethod.PUT, entity, String.class);

        assertThat(acceptResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(acceptResponse.getBody()).isEqualTo("Заявка принята");
    }

    @Test
    void testRejectApplication() {
        ResponseEntity<ApplicationDto> createResponse = createApplication();
        Integer createdId = Objects.requireNonNull(createResponse.getBody()).getId();

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<String> rejectResponse = restTemplate.exchange(baseUrl + "/" + createdId + "/reject", HttpMethod.PUT, entity, String.class);

        assertThat(rejectResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(rejectResponse.getBody()).isEqualTo("Заявка отклонена");
    }

    @Test
    void testDeleteApplication() {
        ResponseEntity<ApplicationDto> createResponse = createApplication();
        Integer createdId = Objects.requireNonNull(createResponse.getBody()).getId();

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<String> deleteResponse = restTemplate.exchange(baseUrl + "/" + createdId, HttpMethod.DELETE, entity, String.class);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deleteResponse.getBody()).isEqualTo("Заявка успешно удалена");
    }
}