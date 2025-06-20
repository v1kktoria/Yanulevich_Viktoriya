package senla.service;

import senla.dto.ParameterDto;

import java.util.List;

public interface ParameterService {

    ParameterDto create(ParameterDto parameterDto);

    ParameterDto getById(Integer id);

    List<ParameterDto> getAll();

    void updateById(Integer id, ParameterDto parameterDto);

    void deleteById(Integer id);
}
