package senla.dao;

import senla.model.Property;

import java.util.List;

public interface PropertyDao extends ParentDao<Property, Integer> {

    List<Property> findByUserId(Integer id);

    List<Property> findAllWithEssentialDetails();
}
