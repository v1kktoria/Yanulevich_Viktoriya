package senla.service.impl;

import senla.dao.impl.ParameterDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Parameter;
import senla.service.ParameterService;
import senla.util.validator.ParameterValidator;

import java.util.List;
import java.util.Optional;

@Component
public class ParameterServiceImpl implements ParameterService {

    @Autowired
    private ParameterDAOImpl parameterDAO;

    @Override
    public Optional<Parameter> create(Parameter parameter) {
        ParameterValidator.validate(parameter);
        return Optional.ofNullable(parameterDAO.create(parameter));
    }

    @Override
    public Optional<Parameter> getById(Integer id) {
        return Optional.ofNullable(parameterDAO.getByParam(id));
    }

    @Override
    public List<Parameter> getAll() {
        return parameterDAO.getAll();
    }

    @Override
    public void updateById(Integer id, Parameter parameter) {
        ParameterValidator.validate(parameter);
        parameterDAO.updateById(id, parameter);
    }

    @Override
    public void deleteById(Integer id) {
        parameterDAO.deleteById(id);
    }
}
