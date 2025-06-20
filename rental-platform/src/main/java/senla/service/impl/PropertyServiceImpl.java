package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.aop.MeasureExecutionTime;
import senla.dto.PropertyDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Property;
import senla.model.Review;
import senla.model.constant.PropertyType;
import senla.repository.PropertyRepository;
import senla.service.PropertyService;
import senla.util.mappers.PropertyMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    private final PropertyMapper propertyMapper;

    @Transactional
    @Override
    public PropertyDto create(PropertyDto propertyDto) {
        Property property = propertyMapper.toEntity(propertyDto);
        property.setCreatedAt(LocalDateTime.now());
        Property savedProperty = propertyRepository.save(property);
        log.info("Недвижимость с ID: {} успешно создана", savedProperty.getId());
        return propertyMapper.toDto(savedProperty);
    }

    @Override
    public PropertyDto getById(Integer id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        log.info("Недвижимость с ID: {} успешно получена", id);
        return propertyMapper.toDto(property);
    }

    @Override
    public List<PropertyDto> getByUserId(Integer id) {
        Sort sort = Sort.by(Sort.Order.desc("rating"));
        List<Property> properties = propertyRepository.findAllByOwnerId(id, sort);
        log.info("Найдено {} недвижимости для пользователя с ID: {}", properties.size(), id);
        return properties.stream()
                .map(propertyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PropertyDto> getAll() {
        Sort sort = Sort.by(Sort.Order.desc("rating"));
        List<Property> properties = propertyRepository.findAll(sort);
        log.info("Найдено {} недвижимости", properties.size());
        return properties.stream()
                .map(propertyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateById(Integer id, PropertyDto propertyDto) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        propertyDto.setId(id);
        propertyMapper.updateEntity(propertyDto, property);
        propertyRepository.save(property);
        log.info("Недвижимость с ID: {} успешно обновлена", id);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        propertyRepository.delete(property);
        log.info("Недвижимость с ID: {} успешно удалена", id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PropertyDto> searchProperties(PropertyType type, Double minPrice, Double maxPrice,
                                              Integer minRooms, Integer maxRooms, String description) {
        Sort sort = Sort.by(Sort.Order.desc("rating"));
        List<Property> properties = propertyRepository.searchProperties(type, minPrice, maxPrice, minRooms,
                maxRooms, description, sort);
        log.info("Найдено {} недвижимости", properties.size());
        return properties.stream()
                .map(propertyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateRating(Integer id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        Double avgRating = property.getReviews().stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        property.setRating(avgRating);
        propertyRepository.save(property);
        log.info("Обновлен рейтинг недвижимости с ID {}: {}", id, avgRating);
    }
}
