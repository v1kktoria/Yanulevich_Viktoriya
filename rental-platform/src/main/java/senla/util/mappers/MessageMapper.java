package senla.util.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import senla.dto.MessageDto;
import senla.model.Chat;
import senla.model.Message;
import senla.model.User;

@Component
@RequiredArgsConstructor
public class MessageMapper {

    private final ModelMapper modelMapper;

    public MessageDto toDto(Message message) {
        MessageDto messageDto = modelMapper.map(message, MessageDto.class);

        messageDto.setCreatedAt(message.getCreatedAt());
        messageDto.setSenderId(message.getSender() != null ? message.getSender().getId() : null);
        messageDto.setChatId(message.getChat() != null ? message.getChat().getId() : null);

        return messageDto;
    }

    public Message toEntity(MessageDto messageDto, Chat chat, User sender) {
        Message message = modelMapper.map(messageDto, Message.class);
        message.setChat(chat);
        message.setSender(sender);
        return message;
    }

    public void updateEntity(MessageDto messageDto, Message message) {
        modelMapper.map(messageDto, message);
    }
}
