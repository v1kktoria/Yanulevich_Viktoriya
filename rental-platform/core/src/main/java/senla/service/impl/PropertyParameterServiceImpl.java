package senla.service.impl;

import senla.dao.PropertyParameterDao;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Parameter;
import senla.model.Property;
import senla.model.PropertyParameter;
import senla.model.id.PropertyParameterId;
import senla.service.PropertyParameterService;
import senla.util.TransactionManager;

import java.util.List;

@Component
public class PropertyParameterServiceImpl implements PropertyParameterService {

    @Autowired
    private PropertyParameterDao propertyParameterDao;

    @Override
    public void create(PropertyParameter propertyParameter) {
        TransactionManager.executeInTransaction(() -> {
            propertyParameterDao.save(propertyParameter);
        });
    }

    @Override
    public PropertyParameter getByPropertyAndParameter(Property property, Parameter parameter) {
        return TransactionManager.executeInTransaction(() -> {
            return propertyParameterDao.findById(PropertyParameterId.builder()
                    .property_id(property.getId())
                    .parameter_id(parameter.getId()).build()
            );
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
            propertyParameterDao.deleteById(PropertyParameterId.builder()
                    .property_id(property.getId())
                    .parameter_id(parameter.getId()).build()
            );
        });
    }
}
