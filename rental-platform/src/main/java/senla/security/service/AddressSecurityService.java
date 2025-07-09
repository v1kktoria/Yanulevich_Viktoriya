package senla.security.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import senla.dto.AddressDto;
import senla.dto.PropertyDto;
import senla.model.User;
import senla.service.AddressService;
import senla.service.PropertyService;
import senla.service.UserService;

@Component
@RequiredArgsConstructor
public class AddressSecurityService {

    private final UserService userService;

    private final PropertyService propertyService;

    private final AddressService addressService;

    public boolean hasAccess(Authentication authentication, Integer addressId) {
        OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        String keycloakId = (String) principal.getAttribute("sub");
        User user = userService.getByKeycloakId(keycloakId);
        AddressDto address = addressService.getById(addressId);
        PropertyDto property = propertyService.getById(address.getPropertyId());
        return property.getOwnerId().equals(user.getId());
    }
}
