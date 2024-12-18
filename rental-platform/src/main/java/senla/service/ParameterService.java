package senla.service;

import senla.model.Parameter;

import java.util.List;
import java.util.Optional;

public interface ParameterService {

    Optional<Parameter> create(Parameter parameter);

    Optional<Parameter> getById(Integer id);

    List<Parameter> getAll();

    void updateById(Integer id, Parameter parameter);

    void deleteById(Integer id);
}
