package senla.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import senla.dto.MessageDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Chat;
import senla.model.Message;
import senla.model.User;
import senla.repository.ChatRepository;
import senla.repository.MessageRepository;
import senla.repository.UserRepository;
import senla.service.MessageService;
import senla.util.mappers.MessageMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final ChatRepository chatRepository;

    private final UserRepository userRepository;

    private final MessageMapper messageMapper;

    @Override
    public MessageDto sendMessage(MessageDto messageDto) {
        Chat chat = chatRepository.findById(messageDto.getChatId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, messageDto.getChatId()));

        User sender = userRepository.findById(messageDto.getSenderId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, messageDto.getSenderId()));

        Message message = messageMapper.toEntity(messageDto, chat, sender);

        message.setCreatedAt(LocalDateTime.now());
        messageRepository.save(message);
        return messageMapper.toDto(message);
    }

    @Override
    public List<MessageDto> getMessagesForChat(Integer chatId) {
        List<Message> messages = messageRepository.findByChatId(chatId);

        return messages.stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }
}
