package senla.util.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import senla.dto.ImageDto;
import senla.model.Image;
import senla.model.Property;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    @Mapping(target = "propertyId", source = "property.id")
    ImageDto toDto(Image image);

    @Mapping(target = "property", expression = "java(property)")
    Image toEntity(ImageDto imageDto, Property property);

    @Mapping(target = "property", ignore = true)
    void updateEntity(ImageDto imageDto,@MappingTarget Image image);
}
