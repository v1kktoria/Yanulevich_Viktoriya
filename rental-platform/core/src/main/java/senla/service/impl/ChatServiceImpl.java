package senla.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dto.ChatDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Chat;
import senla.model.User;
import senla.repository.ChatRepository;
import senla.repository.UserRepository;
import senla.service.ChatService;
import senla.util.mappers.ChatMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    private final UserRepository userRepository;

    private final ChatMapper chatMapper;

    @Transactional
    @Override
    public void create(Integer tenantId, Integer ownerId) {
        User tenant = userRepository.findById(tenantId)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, tenantId));

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, ownerId));

        List<User> users = new ArrayList<>();
        users.add(tenant);
        users.add(owner);

        Chat chat = Chat.builder()
                .users(users)
                .build();

        chatRepository.save(chat);
    }

    @Override
    @Transactional(readOnly = true)
    public ChatDto getById(Integer id) {
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        return chatMapper.toDto(chat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatDto> getChatsForUser(Integer userId) {
        List<Chat> chats = chatRepository.findByUserId(userId);
        return chats.stream()
                .map(chatMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        chatRepository.delete(chat);
    }
}
