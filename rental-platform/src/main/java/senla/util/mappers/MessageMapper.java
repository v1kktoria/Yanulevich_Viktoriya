package senla.util.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import senla.dto.MessageDto;
import senla.model.Chat;
import senla.model.Message;
import senla.model.User;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "chatId", source = "chat.id")
    MessageDto toDto(Message message);

    @Mapping(target = "sender", expression = "java(sender)")
    @Mapping(target = "chat", expression = "java(chat)")
    Message toEntity(MessageDto messageDto, Chat chat, User sender);

    @Mapping(target = "sender", ignore = true)
    @Mapping(target = "chat", ignore = true)
    void updateEntity(MessageDto messageDto,@MappingTarget Message message);
}
