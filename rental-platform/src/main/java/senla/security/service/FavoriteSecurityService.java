package senla.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import senla.model.User;
import senla.service.UserService;

@Component
@RequiredArgsConstructor
public class FavoriteSecurityService {

    private final UserService userService;

    public boolean hasAccess(Authentication authentication, Integer userId) {
        OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        String keycloakId = principal.getAttribute("sub");
        User user = userService.getByKeycloakId(keycloakId);
        return user.getId().equals(userId);
    }
}
