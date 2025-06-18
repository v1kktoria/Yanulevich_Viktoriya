package senla.service;

import senla.dto.PropertyDto;
import senla.model.constant.PropertyType;

import java.util.List;

public interface PropertyService {

    PropertyDto create(PropertyDto propertyDto);

    PropertyDto getById(Integer id);

    List<PropertyDto> getByUserId(Integer id);

    List<PropertyDto> getAll();

    void updateById(Integer id, PropertyDto propertyDto);

    void deleteById(Integer id);

    List<PropertyDto> searchProperties(PropertyType type, Double minPrice, Double maxPrice,
                                       Integer minRooms, Integer maxRooms, String description);

    void updateRating(Integer id);
}
