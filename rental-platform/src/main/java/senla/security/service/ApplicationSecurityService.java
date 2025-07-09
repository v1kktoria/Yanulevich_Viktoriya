package senla.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import senla.dto.ApplicationDto;
import senla.service.ApplicationService;
import senla.service.UserService;

@Component
@RequiredArgsConstructor
public class ApplicationSecurityService {

    private final ApplicationService applicationService;

    private final UserService userService;

    public boolean hasAccess(Authentication authentication, Integer applicationId) {
        Integer userId = extractUserId(authentication);
        ApplicationDto application = applicationService.getById(applicationId);
        return application.getTenantId().equals(userId) || application.getOwnerId().equals(userId);
    }

    public boolean isOwner(Authentication authentication, Integer applicationId) {
        Integer userId = extractUserId(authentication);
        ApplicationDto application = applicationService.getById(applicationId);
        return application.getOwnerId().equals(userId);
    }

    private Integer extractUserId(Authentication authentication) {
        OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        String keycloakId = principal.getAttribute("sub");
        return userService.getByKeycloakId(keycloakId).getId();
    }
}
