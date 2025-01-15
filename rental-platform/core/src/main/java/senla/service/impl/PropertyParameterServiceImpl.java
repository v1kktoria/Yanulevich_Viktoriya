package senla.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senla.dao.PropertyParameterDao;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Parameter;
import senla.model.Property;
import senla.model.PropertyParameter;
import senla.model.id.PropertyParameterId;
import senla.service.PropertyParameterService;
import senla.util.TransactionManager;

import java.util.List;

@Service
public class PropertyParameterServiceImpl implements PropertyParameterService {

    private final PropertyParameterDao propertyParameterDao;

    @Autowired
    public PropertyParameterServiceImpl(PropertyParameterDao propertyParameterDao) {
        this.propertyParameterDao = propertyParameterDao;
    }

    @Override
    public void create(PropertyParameter propertyParameter) {
        TransactionManager.executeInTransaction(() -> {
            propertyParameterDao.save(propertyParameter);
        });
    }

    @Override
    public PropertyParameter getByPropertyAndParameter(Property property, Parameter parameter) {
        return TransactionManager.executeInTransaction(() -> {
            PropertyParameterId id = PropertyParameterId.builder()
                    .property_id(property.getId())
                    .parameter_id(parameter.getId()).build();
            return propertyParameterDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        });
    }

    @Override
    public List<PropertyParameter> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            return propertyParameterDao.findAll();
        });
    }

    @Override
    public void deleteByPropertyAndParameter(Property property, Parameter parameter) {
        TransactionManager.executeInTransaction(() -> {
            PropertyParameterId id = PropertyParameterId.builder()
                    .property_id(property.getId())
                    .parameter_id(parameter.getId()).build();

            PropertyParameter propertyParameter = propertyParameterDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

            propertyParameterDao.delete(propertyParameter);
        });
    }
}
