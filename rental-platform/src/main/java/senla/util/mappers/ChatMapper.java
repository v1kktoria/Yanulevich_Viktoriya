package senla.util.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import senla.dto.ChatDto;
import senla.dto.MessageDto;
import senla.dto.UserDto;
import senla.model.Chat;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatMapper {

    private final ModelMapper modelMapper;

    public ChatDto toDto(Chat chat) {
        ChatDto chatDto = modelMapper.map(chat, ChatDto.class);

        chatDto.setMessages(chat.getMessages().stream()
                .map(message -> modelMapper.map(message, MessageDto.class))
                .toList());

        chatDto.setUsers(chat.getUsers().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList());

        return chatDto;
    }

    public Chat toEntity(ChatDto chatDto) {
        return modelMapper.map(chatDto, Chat.class);
    }

    public void updateEntity(ChatDto chatDto, Chat chat) {
        modelMapper.map(chatDto, chat);
    }
}
