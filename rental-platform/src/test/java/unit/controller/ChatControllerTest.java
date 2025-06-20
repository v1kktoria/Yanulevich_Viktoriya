package unit.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import senla.controller.ChatController;
import senla.dto.ChatDto;
import senla.service.ChatService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ChatControllerTest {

    @Mock
    private ChatService chatService;

    @InjectMocks
    private ChatController chatController;

    private List<ChatDto> chatDtos;
    private ChatDto chatDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        chatDto = new ChatDto();
        chatDto.setId(1);

        chatDtos = List.of(chatDto);
    }

    @Test
    void testGetChatsForUser() {
        when(chatService.getChatsForUser(1)).thenReturn(chatDtos);

        ResponseEntity<List<ChatDto>> response = chatController.getChatsForUser(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(1, response.getBody().get(0).getId());
    }

    @Test
    void testDeleteChat() {
        doNothing().when(chatService).delete(1);

        ResponseEntity<String> response = chatController.deleteChat(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Чат успешно удален", response.getBody());
    }
}
