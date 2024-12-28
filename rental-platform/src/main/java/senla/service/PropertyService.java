package senla.service;

import senla.model.Property;

import java.util.List;
import java.util.Optional;

public interface PropertyService {

    Optional<Property> create(Property property);

    Optional<Property> getById(Integer id);

    Optional<Property> getByUserId(Integer id);

    List<Property> getAll();

    void updateById(Integer id, Property property);

    void deleteById(Integer id);
}
