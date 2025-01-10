package senla.service.impl;

import senla.dao.PropertyDao;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Property;
import senla.service.PropertyService;
import senla.util.TransactionManager;
import senla.util.validator.PropertyValidator;

import java.util.List;

@Component
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyDao propertyDao;

    @Override
    public Property create(Property property) {
        return TransactionManager.executeInTransaction(() -> {
            PropertyValidator.validate(property);
            return propertyDao.save(property);
        });
    }

    @Override
    public Property getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return propertyDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        });
    }

    @Override
    public List<Property> getByUserId(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return propertyDao.findByUserId(id);
        });
    }

    @Override
    public List<Property> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            List<Property> properties = propertyDao.findAll();
            properties.forEach(Property::loadLazyFields);
            return properties;
        });
    }

    @Override
    public void updateById(Integer id, Property property) {
        TransactionManager.executeInTransaction(() -> {
            property.setId(id);
            PropertyValidator.validate(property);
            propertyDao.update(property);
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            Property property = propertyDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
            propertyDao.delete(property);
        });
    }

    @Override
    public List<Property> getAllWithEssentialDetails() {
        return TransactionManager.executeInTransaction(() -> {
            return propertyDao.findAllWithEssentialDetails();
        });
    }
}
