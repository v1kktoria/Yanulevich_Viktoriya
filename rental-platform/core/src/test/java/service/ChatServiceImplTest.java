package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import senla.dto.ChatDto;
import senla.exception.ServiceException;
import senla.model.Chat;
import senla.model.User;
import senla.repository.ChatRepository;
import senla.repository.UserRepository;
import senla.service.impl.ChatServiceImpl;
import senla.util.mappers.ChatMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChatServiceImplTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChatMapper chatMapper;

    @InjectMocks
    private ChatServiceImpl chatService;

    private ChatDto chatDto;
    private Chat chat;
    private User tenant;
    private User owner;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        chatDto = new ChatDto();
        chatDto.setId(1);

        chat = new Chat();
        chat.setId(1);

        tenant = new User();
        tenant.setId(1);

        owner = new User();
        owner.setId(2);
    }

    @Test
    void testCreate() {
        when(userRepository.findById(1)).thenReturn(Optional.of(tenant));
        when(userRepository.findById(2)).thenReturn(Optional.of(owner));
        when(chatRepository.save(any(Chat.class))).thenReturn(chat);

        chatService.create(1, 2);

        verify(chatRepository, times(1)).save(any(Chat.class));
    }

    @Test
    void testCreateUserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> chatService.create(1, 2));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
        verify(chatRepository, never()).save(any());
    }

    @Test
    void testGetById() {
        when(chatRepository.findById(1)).thenReturn(Optional.of(chat));
        when(chatMapper.toDto(chat)).thenReturn(chatDto);

        ChatDto result = chatService.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        when(chatRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> chatService.getById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testGetChatsForUser() {
        when(chatRepository.findByUserId(1)).thenReturn(List.of(chat));
        when(chatMapper.toDto(chat)).thenReturn(chatDto);

        List<ChatDto> result = chatService.getChatsForUser(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
    }

    @Test
    void testDelete() {
        when(chatRepository.findById(1)).thenReturn(Optional.of(chat));
        doNothing().when(chatRepository).delete(chat);

        chatService.delete(1);

        verify(chatRepository, times(1)).delete(chat);
    }

    @Test
    void testDeleteNotFound() {
        when(chatRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> chatService.delete(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }
}
