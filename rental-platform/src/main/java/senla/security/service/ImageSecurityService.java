package senla.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import senla.dto.ImageDto;
import senla.dto.PropertyDto;
import senla.model.User;
import senla.service.ImageService;
import senla.service.PropertyService;
import senla.service.UserService;

@Component
@RequiredArgsConstructor
public class ImageSecurityService {

    private final ImageService imageService;

    private final PropertyService propertyService;

    private final UserService userService;

    public boolean hasAccess(Authentication authentication, Integer imageId) {
        OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        String keycloakId = (String) principal.getAttribute("sub");
        User user = userService.getByKeycloakId(keycloakId);
        ImageDto image = imageService.getById(imageId);
        PropertyDto property = propertyService.getById(image.getPropertyId());
        return property.getOwnerId().equals(user.getId());
    }
}
