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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyDAOImpl propertyDAO;

    @Autowired
    private UserDAOImpl userDAO;

    @Override
    public Property create(Property property) {
        validate(property);
        return propertyDAO.create(property);
    }

    @Override
    public Property getById(Integer id) {
        return propertyDAO.getByParam(id);
    }

    @Override
    public Property getByUserId(Integer id) {
        User user = userDAO.getByParam(id);
        return propertyDAO.getByParam(user);
    }

    @Override
    public List<Property> getAll() {
        return propertyDAO.getAll();
    }

    @Override
    public void updateById(Integer id, Property property) {
        validate(property);
        propertyDAO.updateById(id, property);
    }

    @Override
    public void deleteById(Integer id) {
        propertyDAO.deleteById(id);
    }

    private void validate(Property property) {
        if (property.getArea() < 0) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Площадь не может быть меньше нуля");
        } else if (property.getRooms() < 0) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Количество комнат не может быть меньше нуля");
        } else if (property.getPrice() < 0) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Цена не может быть меньше нуля");
        }
    }
}
