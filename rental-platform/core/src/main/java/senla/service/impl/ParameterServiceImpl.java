package senla.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import senla.dao.ParameterDao;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Parameter;
import senla.service.ParameterService;
import senla.util.TransactionManager;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParameterServiceImpl implements ParameterService {

    private final ParameterDao parameterDao;

    @Override
    public Parameter create(Parameter parameter) {
        return TransactionManager.executeInTransaction(() -> {
            return parameterDao.save(parameter);
        });
    }

    @Override
    public Parameter getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return parameterDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
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
            parameterDao.update(parameter);
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            Parameter parameter = parameterDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
            parameterDao.delete(parameter);
        });
    }
}
