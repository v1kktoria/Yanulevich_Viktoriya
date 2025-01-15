package senla.service;

import senla.model.Property;

import java.util.List;
import java.util.Optional;

public interface PropertyService {

    Property create(Property property);

    Property getById(Integer id);

    List<Property> getByUserId(Integer id);

    List<Property> getAll();

    void updateById(Integer id, Property property);

    void deleteById(Integer id);

    List<Property> getAllWithEssentialDetails();
}
