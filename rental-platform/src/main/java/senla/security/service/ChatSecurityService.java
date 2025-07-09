package senla.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import senla.model.User;
import senla.service.ChatService;
import senla.service.UserService;

@Component
@RequiredArgsConstructor
public class ChatSecurityService {

    private final ChatService chatService;

    private final UserService userService;

    public boolean hasAccess(Authentication authentication, Integer chatId) {
        OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        String keycloakId = (String) principal.getAttribute("sub");
        User user = userService.getByKeycloakId(keycloakId);

        return chatService.getById(chatId).getUsers().stream()
                .anyMatch(u -> u.getId().equals(user.getId()));
    }

    public boolean isCurrentUser(Authentication authentication, Integer userId) {
        String keycloakId = ((OAuth2AuthenticatedPrincipal) authentication.getPrincipal()).getAttribute("sub");
        Integer currentUserId = userService.getByKeycloakId(keycloakId).getId();
        return currentUserId.equals(userId);
    }
}
