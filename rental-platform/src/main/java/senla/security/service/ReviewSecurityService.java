package senla.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import senla.service.ReviewService;
import senla.service.UserService;

@Component
@RequiredArgsConstructor
public class ReviewSecurityService {

    private final UserService userService;

    private final ReviewService reviewService;

    public boolean hasAccess(Authentication authentication, Integer reviewId) {
        OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        String keycloakId = principal.getAttribute("sub");
        Integer userId = userService.getByKeycloakId(keycloakId).getId();
        Integer reviewAuthorId = reviewService.getById(reviewId).getUserId();
        return userId.equals(reviewAuthorId);
    }
}
