package senla.util.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import senla.dto.PropertyParameterDto;
import senla.model.PropertyParameter;
import senla.model.id.PropertyParameterId;

@Component
@RequiredArgsConstructor
public class PropertyParameterMapper {

    private final ModelMapper modelMapper;

    public PropertyParameterDto toDto(PropertyParameter propertyParameter) {
        PropertyParameterDto propertyParameterDto = modelMapper.map(propertyParameter, PropertyParameterDto.class);

        PropertyParameterId id = propertyParameter.getId();
        propertyParameterDto.setPropertyId(id.getProperty_id());
        propertyParameterDto.setParameterId(id.getParameter_id());

        return propertyParameterDto;
    }

    public PropertyParameter toEntity(PropertyParameterDto propertyParameterDto) {
        PropertyParameter propertyParameter = modelMapper.map(propertyParameterDto, PropertyParameter.class);

        PropertyParameterId id = PropertyParameterId.builder()
                .parameter_id(propertyParameterDto.getParameterId())
                .property_id(propertyParameterDto.getPropertyId())
                .build();
        propertyParameter.setId(id);

        return propertyParameter;
    }

}
