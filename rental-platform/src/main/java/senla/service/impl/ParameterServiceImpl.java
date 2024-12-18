package senla.service.impl;

import senla.dao.impl.ParameterDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Parameter;
import senla.service.ParameterService;

import java.util.List;

@Component
public class ParameterServiceImpl implements ParameterService {

    @Autowired
    private ParameterDAOImpl parameterDAO;

    @Override
    public Parameter create(Parameter parameter) {
        validate(parameter);
        return parameterDAO.create(parameter);
    }

    @Override
    public Parameter getById(Integer id) {
        return parameterDAO.getByParam(id);
    }

    @Override
    public List<Parameter> getAll() {
        return parameterDAO.getAll();
    }

    @Override
    public void updateById(Integer id, Parameter parameter) {
        validate(parameter);
        parameterDAO.updateById(id, parameter);
    }

    @Override
    public void deleteById(Integer id) {
        parameterDAO.deleteById(id);
    }

    private void validate(Parameter parameter) {
        if (parameter.getName().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Название параметра не может быть пустым");
        }
    }
}
