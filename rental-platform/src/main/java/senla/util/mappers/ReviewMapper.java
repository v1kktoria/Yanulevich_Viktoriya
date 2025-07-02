package senla.util.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import senla.dto.ReviewDto;
import senla.model.Property;
import senla.model.Review;
import senla.model.User;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "propertyId", source = "property.id")
    @Mapping(target = "userId", source = "user.id")
    ReviewDto toDto(Review review);

    @Mapping(target = "property", expression = "java(property)")
    @Mapping(target = "user", expression = "java(user)")
    @Mapping(target = "rating", source = "reviewDto.rating")
    @Mapping(target = "createdAt", ignore = true)
    Review toEntity(ReviewDto reviewDto, Property property, User user);

    @Mapping(target = "property", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntity(ReviewDto reviewDto,@MappingTarget Review review);
}
