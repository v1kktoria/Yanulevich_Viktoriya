package senla.service;

import senla.model.Property;

import java.util.List;

public interface PropertyService {

    Property create(Property property);

    Property getById(Integer id);

    Property getByUserId(Integer id);

    List<Property> getAll();

    void updateById(Integer id, Property property);

    void deleteById(Integer id);
}
