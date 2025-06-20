package senla.service;

import senla.dto.MessageDto;

import java.util.List;

public interface MessageService {

    MessageDto sendMessage(MessageDto messageDto);

    List<MessageDto> getMessagesForChat(Integer chatId);
}
