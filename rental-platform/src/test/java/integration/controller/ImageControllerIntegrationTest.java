package integration.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    private String jwtToken;

    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/images";

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
        Integer createdId = createImageForTest();
        assertThat(createdId).isNotNull();
    }

    @Test
    void testGetImageById() {
        Integer createdId = createImageForTest();

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<ImageDto> response = restTemplate.exchange(baseUrl + "/" + createdId, HttpMethod.GET, entity, ImageDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(createdId);
    }

    @Test
    void testDeleteImage() {
        Integer createdId = createImageForTest();

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/" + createdId, HttpMethod.DELETE, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Изображение успешно удалено");
    }

    private Integer createImageForTest() {
        ByteArrayResource fileResource = new ByteArrayResource("test image content".getBytes()) {

            @Override
            public String getFilename() {
                return "test.jpg";
            }

        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileResource);
        body.add("propertyId", "1");

        HttpHeaders multipartHeaders = new HttpHeaders();
        multipartHeaders.set("Authorization", "Bearer " + jwtToken);
        multipartHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, multipartHeaders);

        ResponseEntity<ImageDto> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, ImageDto.class);
        return Objects.requireNonNull(response.getBody()).getId();
    }
}
