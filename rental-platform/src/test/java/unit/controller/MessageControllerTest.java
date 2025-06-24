package unit.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import senla.controller.MessageController;
import senla.dto.MessageDto;
import senla.service.MessageService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MessageControllerTest {

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageController messageController;

    private MessageDto messageDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        messageDto = new MessageDto();
        messageDto.setId(1);
        messageDto.setSenderId(1);
        messageDto.setChatId(1);
        messageDto.setContent("Test message");
    }

    @Test
    void testSendMessage() {
        when(messageService.sendMessage(messageDto)).thenReturn(messageDto);

        ResponseEntity<MessageDto> response = messageController.sendMessage(messageDto);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Test message", response.getBody().getContent());
    }

    @Test
    void testGetMessagesForChat() {
        when(messageService.getMessagesForChat(1)).thenReturn(List.of(messageDto));

        ResponseEntity<List<MessageDto>> response = messageController.getMessagesForChat(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Test message", response.getBody().get(0).getContent());
    }
}
