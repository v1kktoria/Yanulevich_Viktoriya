package senla.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import senla.aop.MeasureExecutionTime;
import senla.dto.ChatDto;
import senla.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/user/{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<List<ChatDto>> getChatsForUser(@PathVariable("userId") Integer userId) {
        log.info("Запрос чатов для пользователя с ID: {}", userId);
        List<ChatDto> chats = chatService.getChatsForUser(userId);
        return ResponseEntity.ok(chats);
    }

    @DeleteMapping("/{chatId}")
    @PreAuthorize("@chatSecurityService.hasAccess(authentication, #chatId)")
    public ResponseEntity<String> deleteChat(@PathVariable Integer chatId) {
        log.info("Удаление чата с ID: {}", chatId);
        chatService.delete(chatId);
        return ResponseEntity.ok("Чат успешно удален");
    }

}