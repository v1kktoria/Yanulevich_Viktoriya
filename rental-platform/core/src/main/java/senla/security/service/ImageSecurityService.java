package senla.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import senla.dto.ImageDto;
import senla.dto.PropertyDto;
import senla.model.User;
import senla.service.ImageService;
import senla.service.PropertyService;

@Component
@RequiredArgsConstructor
public class ImageSecurityService {

    private final ImageService imageService;

    private final PropertyService propertyService;

    public boolean hasAccess(Authentication authentication, Integer imageId) {
        User user = (User) authentication.getPrincipal();
        ImageDto image = imageService.getById(imageId);
        PropertyDto property = propertyService.getById(image.getPropertyId());
        return property.getOwnerId().equals(user.getId());
    }
}
