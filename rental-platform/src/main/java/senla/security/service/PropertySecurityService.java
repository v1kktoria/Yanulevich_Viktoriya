package senla.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import senla.service.PropertyService;
import senla.service.UserService;

@Component
@RequiredArgsConstructor
public class PropertySecurityService {

    private final UserService userService;

    private final PropertyService propertyService;

    public boolean hasAccess(Authentication authentication, Integer propertyId) {
        OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        String keycloakId = principal.getAttribute("sub");
        Integer userId = userService.getByKeycloakId(keycloakId).getId();
        Integer ownerId = propertyService.getById(propertyId).getOwnerId();
        return userId.equals(ownerId);
    }
}
