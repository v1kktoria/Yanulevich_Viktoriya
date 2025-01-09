package senla.service.impl;

import senla.dao.impl.PropertyParameterDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Parameter;
import senla.model.Property;
import senla.model.PropertyParameter;
import senla.model.id.PropertyParameterId;
import senla.service.PropertyParameterService;
import senla.util.TransactionManager;

import java.util.List;
import java.util.Optional;

@Component
public class PropertyParameterServiceImpl implements PropertyParameterService {

    @Autowired
    private PropertyParameterDAOImpl propertyParameterDAO;

    @Override
    public void create(PropertyParameter propertyParameter) {
        TransactionManager.executeInTransaction(() -> {
            propertyParameterDAO.save(propertyParameter);
            return Optional.empty();
        });
    }

    @Override
    public Optional<PropertyParameter> getByPropertyAndParameter(Property property, Parameter parameter) {
        return TransactionManager.executeInTransaction(() -> {
            return Optional.ofNullable(propertyParameterDAO.findById(PropertyParameterId.builder()
                    .property_id(property.getId())
                    .parameter_id(parameter.getId()).build()
            ));
        });
    }

    @Override
    public List<PropertyParameter> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            return propertyParameterDAO.findAll();
        });
    }

    @Override
    public void deleteByPropertyAndParameter(Property property, Parameter parameter) {
        TransactionManager.executeInTransaction(() -> {
            propertyParameterDAO.deleteById(PropertyParameterId.builder()
                    .property_id(property.getId())
                    .parameter_id(parameter.getId()).build()
            );
            return Optional.empty();
        });
    }
}
