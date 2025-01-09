package senla.service.impl;

import senla.dao.impl.PropertyDAOImpl;
import senla.dao.impl.UserDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Property;
import senla.model.User;
import senla.service.PropertyService;
import senla.util.TransactionManager;
import senla.util.validator.PropertyValidator;

import java.util.List;
import java.util.Optional;

@Component
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyDAOImpl propertyDAO;

    @Autowired
    private UserDAOImpl userDAO;

    @Override
    public Optional<Property> create(Property property) {
        return TransactionManager.executeInTransaction(() -> {
            PropertyValidator.validate(property);
            return Optional.ofNullable(propertyDAO.save(property));
        });
    }

    @Override
    public Optional<Property> getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return Optional.ofNullable(propertyDAO.findById(id));
        });
    }

    @Override
    public List<Property> getByUserId(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            User user = userDAO.findById(id);
            return propertyDAO.findByUser(user);
        });
    }

    @Override
    public List<Property> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            List<Property> properties = propertyDAO.findAll();
            properties.forEach(Property::loadLazyFields);
            return properties;
        });
    }

    @Override
    public void updateById(Integer id, Property property) {
        TransactionManager.executeInTransaction(() -> {
            property.setId(id);
            PropertyValidator.validate(property);
            propertyDAO.update(property);
            return Optional.empty();
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            propertyDAO.deleteById(id);
            return Optional.empty();
        });
    }

    @Override
    public List<Property> getAllWithEssentialDetails() {
        return TransactionManager.executeInTransaction(() -> {
            return propertyDAO.findAllWithEssentialDetails();
        });
    }
}
