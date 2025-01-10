package senla.service.impl;

import senla.dao.ParameterDao;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Parameter;
import senla.service.ParameterService;
import senla.util.TransactionManager;
import senla.util.validator.ParameterValidator;

import java.util.List;

@Component
public class ParameterServiceImpl implements ParameterService {

    @Autowired
    private ParameterDao parameterDao;

    @Override
    public Parameter create(Parameter parameter) {
        return TransactionManager.executeInTransaction(() -> {
            ParameterValidator.validate(parameter);
            return parameterDao.save(parameter);
        });
    }

    @Override
    public Parameter getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return parameterDao.findById(id);
        });
    }

    @Override
    public List<Parameter> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            return parameterDao.findAll();
        });
    }

    @Override
    public void updateById(Integer id, Parameter parameter) {
        TransactionManager.executeInTransaction(() -> {
            parameter.setId(id);
            ParameterValidator.validate(parameter);
            parameterDao.update(parameter);
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            parameterDao.deleteById(id);
        });
    }
}
