package senla.util.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import senla.dto.ChatDto;
import senla.model.Chat;

@Mapper(componentModel = "spring", uses = {MessageMapper.class, UserMapper.class})
public interface ChatMapper {

    ChatDto toDto(Chat chat);

    Chat toEntity(ChatDto chatDto);

    void updateEntity(ChatDto chatDto,@MappingTarget Chat chat);
}
