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
import senla.dto.ImageDto;
import senla.dto.UserDto;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ImageControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    private ImageDto imageDto;

    private String jwtToken;

    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/images";
        imageDto = new ImageDto();
        imageDto.setPropertyId(1);
        imageDto.setImageUrl("http://example.com/image.jpg");

        UserDto userDto = new UserDto();
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
    void testCreateImage() {
        HttpEntity<ImageDto> entity = new HttpEntity<>(imageDto, headers);
        ResponseEntity<ImageDto> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, ImageDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
    }

    @Test
    void testGetImageById() {
        ResponseEntity<ImageDto> createResponse = restTemplate.exchange(baseUrl, HttpMethod.POST, new HttpEntity<>(imageDto, headers), ImageDto.class);
        Integer createdId = Objects.requireNonNull(createResponse.getBody()).getId();

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<ImageDto> getResponse = restTemplate.exchange(baseUrl + "/" + createdId, HttpMethod.GET, entity, ImageDto.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getId()).isEqualTo(createdId);
    }

    @Test
    void testDeleteImage() {
        ResponseEntity<ImageDto> createResponse = restTemplate.exchange(baseUrl, HttpMethod.POST, new HttpEntity<>(imageDto, headers), ImageDto.class);
        Integer createdId = Objects.requireNonNull(createResponse.getBody()).getId();

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<String> deleteResponse = restTemplate.exchange(baseUrl + "/" + createdId, HttpMethod.DELETE, entity, String.class);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deleteResponse.getBody()).isEqualTo("Изображение успешно удалено");
    }
}
