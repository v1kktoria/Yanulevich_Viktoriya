package senla.util.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import senla.dto.AddressDto;
import senla.model.Address;
import senla.model.Property;

@Component
@RequiredArgsConstructor
public class AddressMapper {

    private final ModelMapper modelMapper;

    public AddressDto toDto(Address address) {
        AddressDto addressDto = modelMapper.map(address, AddressDto.class);
        addressDto.setPropertyId(address.getProperty() != null ? address.getProperty().getId() : null);
        return addressDto;
    }

    public Address toEntity(AddressDto addressDto, Property property) {
        Address address = modelMapper.map(addressDto, Address.class);
        address.setProperty(property);
        return address;
    }

    public void updateEntity(AddressDto addressDto, Address address) {
        modelMapper.map(addressDto, address);
    }
}
