package senla.service.impl;

import senla.dao.impl.ParameterDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Parameter;
import senla.service.ParameterService;
import senla.util.TransactionManager;
import senla.util.validator.ParameterValidator;

import java.util.List;
import java.util.Optional;

@Component
public class ParameterServiceImpl implements ParameterService {

    @Autowired
    private ParameterDAOImpl parameterDAO;

    @Override
    public Optional<Parameter> create(Parameter parameter) {
        return TransactionManager.executeInTransaction(() -> {
            ParameterValidator.validate(parameter);
            return Optional.ofNullable(parameterDAO.save(parameter));
        });
    }

    @Override
    public Optional<Parameter> getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return Optional.ofNullable(parameterDAO.findById(id));
        });
    }

    @Override
    public List<Parameter> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            return parameterDAO.findAll();
        });
    }

    @Override
    public void updateById(Integer id, Parameter parameter) {
        TransactionManager.executeInTransaction(() -> {
            parameter.setId(id);
            ParameterValidator.validate(parameter);
            parameterDAO.update(parameter);
            return Optional.empty();
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            parameterDAO.deleteById(id);
            return Optional.empty();
        });
    }
}
