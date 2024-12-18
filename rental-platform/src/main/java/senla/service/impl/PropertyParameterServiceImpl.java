package senla.service.impl;

import senla.dao.PropertyParameterDAO;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Parameter;
import senla.model.Property;
import senla.model.PropertyParameter;
import senla.service.PropertyParameterService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PropertyParameterServiceImpl implements PropertyParameterService {

    @Autowired
    private PropertyParameterDAO propertyParameterDAO;

    @Override
    public void create(PropertyParameter propertyParameter) {
        propertyParameterDAO.create(propertyParameter);
    }

    @Override
    public PropertyParameter getByPropertyAndParameter(Property property, Parameter parameter) {
        return propertyParameterDAO.getByPropertyAndParameter(property, parameter);
    }

    @Override
    public List<PropertyParameter> getAll() {
        return propertyParameterDAO.getAll();
    }

    @Override
    public void deleteByPropertyAndParameter(Property property, Parameter parameter) {
        propertyParameterDAO.deleteByPropertyAndParameter(property, parameter);
    }
}
