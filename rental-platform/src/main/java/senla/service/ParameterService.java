package senla.service;

import senla.model.Parameter;

import java.util.List;

public interface ParameterService {

    Parameter create(Parameter parameter);

    Parameter getById(Integer id);

    List<Parameter> getAll();

    void updateById(Integer id, Parameter parameter);

    void deleteById(Integer id);
}
