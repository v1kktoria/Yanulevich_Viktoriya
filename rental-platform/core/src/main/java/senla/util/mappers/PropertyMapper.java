package senla.util.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import senla.dto.AddressDto;
import senla.dto.AnalyticsDto;
import senla.dto.ImageDto;
import senla.dto.PropertyDto;
import senla.dto.PropertyParameterDto;
import senla.dto.ReviewDto;
import senla.model.Property;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PropertyMapper {

    private final ModelMapper modelMapper;

    public PropertyDto toDto(Property property) {
        PropertyDto propertyDto = modelMapper.map(property, PropertyDto.class);

        propertyDto.setPropertiesParameters(property.getPropertyParameters() != null ? property.getPropertyParameters().stream()
                .map(propertyParameter -> modelMapper.map(propertyParameter, PropertyParameterDto.class))
                .collect(Collectors.toSet()) : new HashSet<>());

        propertyDto.setImages(property.getImages() != null ? property.getImages().stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .collect(Collectors.toSet()) : new HashSet<>());

        propertyDto.setReviews(property.getReviews() != null ? property.getReviews().stream()
                .map(review -> modelMapper.map(review, ReviewDto.class))
                .collect(Collectors.toSet()) : new HashSet<>());

        propertyDto.setAnalytics(property.getAnalytics() != null ? modelMapper.map(property.getAnalytics(), AnalyticsDto.class) : null);
        propertyDto.setAddress(property.getAddress() != null ? modelMapper.map(property.getAddress(), AddressDto.class) : null);

        return propertyDto;
    }

    public Property toEntity(PropertyDto propertyDto) {
        return modelMapper.map(propertyDto, Property.class);
    }

    public void updateEntity(PropertyDto propertyDto, Property property) {
        modelMapper.map(propertyDto, property);
    }
}
