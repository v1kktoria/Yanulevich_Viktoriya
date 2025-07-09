package senla.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import senla.service.ProfileService;
import senla.service.UserService;

@Component
@RequiredArgsConstructor
public class ProfileSecurityService {

    private final UserService userService;

    private final ProfileService profileService;

    public boolean hasAccess(Authentication authentication, Integer profileId) {
        OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        String keycloakId = principal.getAttribute("sub");
        Integer userId = userService.getByKeycloakId(keycloakId).getId();
        Integer profileOwnerId = profileService.getById(profileId).getUserId();
        return userId.equals(profileOwnerId);
    }
}
