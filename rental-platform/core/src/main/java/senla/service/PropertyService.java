package senla.service;

import senla.dto.PropertyDto;

import java.util.List;

public interface PropertyService {

    PropertyDto create(PropertyDto propertyDto);

    PropertyDto getById(Integer id);

    List<PropertyDto> getByUserId(Integer id);

    List<PropertyDto> getAll();

    void updateById(Integer id, PropertyDto propertyDto);

    void deleteById(Integer id);

    List<PropertyDto> getAllWithEssentialDetails();
}
