package integration.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import senla.Main;
import senla.dto.ChatDto;
import senla.dto.UserDto;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ChatControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;
    private String jwtToken;
    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/chats";

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
    void testGetChatsForUser() {
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<ChatDto[]> response = restTemplate.exchange(baseUrl + "/user/1", HttpMethod.GET, entity, ChatDto[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
    }

    @Test
    void testDeleteChat() {
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<String> deleteResponse = restTemplate.exchange(baseUrl + "/1", HttpMethod.DELETE, entity, String.class);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deleteResponse.getBody()).isEqualTo("Чат успешно удален");
    }
}
