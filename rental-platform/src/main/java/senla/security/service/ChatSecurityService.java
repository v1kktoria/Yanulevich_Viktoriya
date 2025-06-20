package senla.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import senla.model.User;
import senla.service.ChatService;

@Component
@RequiredArgsConstructor
public class ChatSecurityService {

    private final ChatService chatService;

    public boolean hasAccess(Authentication authentication, Integer chatId) {
        User user = (User) authentication.getPrincipal();

        return chatService.getById(chatId).getUsers().stream()
                .anyMatch(u -> u.getId().equals(user.getId()));
    }
}
