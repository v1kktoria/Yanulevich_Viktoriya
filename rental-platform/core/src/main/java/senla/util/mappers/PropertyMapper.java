package senla.util.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import senla.dto.*;
import senla.model.Parameter;
import senla.model.Property;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PropertyMapper {

    private final ModelMapper modelMapper;

    public PropertyDto toDto(Property property) {
        PropertyDto propertyDto = modelMapper.map(property, PropertyDto.class);

        propertyDto.setParameters(property.getParameters() != null ? property.getParameters().stream()
                .map(propertyParameter -> modelMapper.map(propertyParameter, ParameterDto.class))
                .collect(Collectors.toList()) : new ArrayList<>());

        propertyDto.setImages(property.getImages() != null ? property.getImages().stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .collect(Collectors.toList()) : new ArrayList<>());

        propertyDto.setReviews(property.getReviews() != null ? property.getReviews().stream()
                .map(review -> modelMapper.map(review, ReviewDto.class))
                .collect(Collectors.toList()) : new ArrayList<>());

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
