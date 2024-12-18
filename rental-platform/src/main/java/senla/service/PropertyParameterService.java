package senla.service;

import senla.model.Parameter;
import senla.model.Property;
import senla.model.PropertyParameter;

import java.util.List;
import java.util.Optional;

public interface PropertyParameterService {

    void create(PropertyParameter propertyParameter);

    Optional<PropertyParameter> getByPropertyAndParameter(Property property, Parameter parameter);

    List<PropertyParameter> getAll();

    void deleteByPropertyAndParameter(Property property, Parameter parameter);
}
