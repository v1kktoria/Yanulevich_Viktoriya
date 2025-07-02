package senla.util.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import senla.dto.AddressDto;
import senla.model.Address;
import senla.model.Property;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "propertyId", source = "property.id")
    AddressDto toDto(Address address);

    @Mapping(target = "property", expression = "java(property)")
    Address toEntity(AddressDto addressDto, Property property);

    @Mapping(target = "property", ignore = true)
    void updateEntity(AddressDto addressDto, @MappingTarget Address address);
}
