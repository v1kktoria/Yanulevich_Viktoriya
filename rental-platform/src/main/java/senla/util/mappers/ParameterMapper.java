package senla.util.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import senla.dto.ParameterDto;
import senla.model.Parameter;

@Mapper(componentModel = "spring")
public interface ParameterMapper {

    ParameterDto toDto(Parameter parameter);

    Parameter toEntity(ParameterDto parameterDto);

    void updateEntity(ParameterDto parameterDto,@MappingTarget Parameter parameter);
}
