package senla.service;

import senla.dto.ChatDto;

import java.util.List;

public interface ChatService {

    void create(Integer tenantId, Integer ownerId);

    ChatDto getById(Integer id);

    List<ChatDto> getChatsForUser(Integer userId);

    void delete(Integer id);
}
