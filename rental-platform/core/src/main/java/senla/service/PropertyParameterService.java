package senla.service;

import senla.dto.ParameterDto;
import senla.dto.PropertyDto;
import senla.dto.PropertyParameterDto;

import java.util.List;

public interface PropertyParameterService {

    void create(PropertyParameterDto propertyParameterDto);

    PropertyParameterDto getByPropertyAndParameter(PropertyDto property, ParameterDto parameter);

    List<PropertyParameterDto> getAll();

    void deleteByPropertyAndParameter(PropertyDto property, ParameterDto parameter);
}
