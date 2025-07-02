package senla.util.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import senla.dto.ImageDto;
import senla.model.Image;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    @Mapping(target = "propertyId", source = "property.id")
    ImageDto toDto(Image image);
}
