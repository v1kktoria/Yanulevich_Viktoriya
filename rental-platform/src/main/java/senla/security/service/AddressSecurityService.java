package senla.security.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import senla.dto.AddressDto;
import senla.dto.PropertyDto;
import senla.model.User;
import senla.service.AddressService;
import senla.service.PropertyService;

@Component
@RequiredArgsConstructor
public class AddressSecurityService {

    private final PropertyService propertyService;

    private final AddressService addressService;

    public boolean hasAccess(Authentication authentication, Integer addressId) {
        User user = (User) authentication.getPrincipal();
        AddressDto address = addressService.getById(addressId);
        PropertyDto property = propertyService.getById(address.getPropertyId());
        return property.getOwnerId().equals(user.getId());
    }
}
