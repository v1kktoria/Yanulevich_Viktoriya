package senla.util.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import senla.dto.ParameterDto;
import senla.model.Parameter;

@Component
@RequiredArgsConstructor
public class ParameterMapper {

    private final ModelMapper modelMapper;

    public ParameterDto toDto(Parameter parameter) {
        return modelMapper.map(parameter, ParameterDto.class);
    }

    public Parameter toEntity(ParameterDto parameterDto) {
        return modelMapper.map(parameterDto, Parameter.class);
    }

    public void updateEntity(ParameterDto parameterDto, Parameter parameter) {
        modelMapper.map(parameterDto, parameter);
    }
}
