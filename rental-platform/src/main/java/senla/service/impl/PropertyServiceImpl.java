package senla.service.impl;

import senla.dao.impl.PropertyDAOImpl;
import senla.dao.impl.UserDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Property;
import senla.model.User;
import senla.service.PropertyService;
import senla.util.validator.PropertyValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyDAOImpl propertyDAO;

    @Autowired
    private UserDAOImpl userDAO;

    @Override
    public Optional<Property> create(Property property) {
        PropertyValidator.validate(property);
        return Optional.ofNullable(propertyDAO.create(property));
    }

    @Override
    public Optional<Property> getById(Integer id) {
        return Optional.ofNullable(propertyDAO.getByParam(id));
    }

    @Override
    public Optional<Property> getByUserId(Integer id) {
        User user = userDAO.getByParam(id);
        return Optional.ofNullable(propertyDAO.getByParam(user));
    }

    @Override
    public List<Property> getAll() {
        return propertyDAO.getAll();
    }

    @Override
    public void updateById(Integer id, Property property) {
        PropertyValidator.validate(property);
        propertyDAO.updateById(id, property);
    }

    @Override
    public void deleteById(Integer id) {
        propertyDAO.deleteById(id);
    }

}
