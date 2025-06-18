package senla.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import senla.dto.MessageDto;
import senla.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @PreAuthorize("#messageDto.senderId == authentication.principal.id")
    public ResponseEntity<MessageDto> sendMessage(@RequestBody @Valid MessageDto messageDto) {
        log.info("Отправка сообщения с данными: {}", messageDto);
        MessageDto message = messageService.sendMessage(messageDto);
        return ResponseEntity.status(201).body(message);
    }

    @GetMapping("/chat/{chatId}")
    @PreAuthorize("@chatServiceImpl.getById(chatId).users.contains(authentication.principal.id)")
    public ResponseEntity<List<MessageDto>> getMessagesForChat(@PathVariable("chatId") Integer chatId) {
        log.info("Запрос сообщений для чата с ID: {}", chatId);
        List<MessageDto> messages = messageService.getMessagesForChat(chatId);
        return ResponseEntity.ok(messages);
    }
}
