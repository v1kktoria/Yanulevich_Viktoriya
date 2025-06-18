package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import senla.dto.MessageDto;
import senla.exception.ServiceException;
import senla.model.Chat;
import senla.model.Message;
import senla.model.User;
import senla.repository.ChatRepository;
import senla.repository.MessageRepository;
import senla.repository.UserRepository;
import senla.service.impl.MessageServiceImpl;
import senla.util.mappers.MessageMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MessageServiceImplTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageMapper messageMapper;

    @InjectMocks
    private MessageServiceImpl messageService;

    private MessageDto messageDto;
    private Message message;
    private Chat chat;
    private User sender;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        messageDto = new MessageDto();
        messageDto.setChatId(1);
        messageDto.setSenderId(1);
        messageDto.setContent("Test message");

        chat = new Chat();
        chat.setId(1);

        sender = new User();
        sender.setId(1);

        message = new Message();
        message.setId(1);
        message.setChat(chat);
        message.setSender(sender);
        message.setContent("Test message");
        message.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testSendMessage() {
        when(chatRepository.findById(1)).thenReturn(Optional.of(chat));
        when(userRepository.findById(1)).thenReturn(Optional.of(sender));
        when(messageMapper.toEntity(messageDto, chat, sender)).thenReturn(message);
        when(messageRepository.save(message)).thenReturn(message);
        when(messageMapper.toDto(message)).thenReturn(messageDto);

        MessageDto result = messageService.sendMessage(messageDto);

        assertNotNull(result);
        assertEquals("Test message", result.getContent());
        verify(messageRepository, times(1)).save(message);
    }

    @Test
    void testSendMessageChatNotFound() {
        when(chatRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> messageService.sendMessage(messageDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
        verify(messageRepository, never()).save(any());
    }

    @Test
    void testSendMessageSenderNotFound() {
        when(chatRepository.findById(1)).thenReturn(Optional.of(chat));
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> messageService.sendMessage(messageDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
        verify(messageRepository, never()).save(any());
    }

    @Test
    void testGetMessagesForChat() {
        when(messageRepository.findByChatId(1)).thenReturn(Collections.singletonList(message));
        when(messageMapper.toDto(message)).thenReturn(messageDto);

        List<MessageDto> messages = messageService.getMessagesForChat(1);

        assertNotNull(messages);
        assertEquals(1, messages.size());
        assertEquals("Test message", messages.get(0).getContent());
    }

    @Test
    void testGetMessagesForChatNotFound() {
        when(messageRepository.findByChatId(1)).thenReturn(Collections.emptyList());

        List<MessageDto> messages = messageService.getMessagesForChat(1);

        assertNotNull(messages);
        assertTrue(messages.isEmpty());
    }
}