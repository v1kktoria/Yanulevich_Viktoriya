package senla.util.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import senla.dto.*;
import senla.model.Property;
import senla.model.User;

@Mapper(
        componentModel = "spring",
        uses = {ParameterMapper.class, ImageMapper.class, ReviewMapper.class, AddressMapper.class}
)
public interface PropertyMapper {

    PropertyDto toDto(Property property);

    @Mapping(target = "owner", expression = "java(owner)")
    @Mapping(target = "reviews", ignore = true)
    Property toEntity(PropertyDto propertyDto, User owner);

    void updateEntity(PropertyDto propertyDto,@MappingTarget Property property);
}
