package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dao.PropertyDao;
import senla.dto.PropertyDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Property;
import senla.service.PropertyService;
import senla.util.mappers.PropertyMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@MeasureExecutionTime
public class PropertyServiceImpl implements PropertyService {

    private final PropertyDao propertyDao;

    private final PropertyMapper propertyMapper;

    @Override
    public PropertyDto create(PropertyDto propertyDto) {
        Property property = propertyMapper.toEntity(propertyDto);
        Property savedProperty = propertyDao.save(property);
        log.info("Недвижимость с ID: {} успешно создана", savedProperty.getId());
        return propertyMapper.toDto(savedProperty);
    }

    @Override
    public PropertyDto getById(Integer id) {
        Property property = propertyDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        log.info("Недвижимость с ID: {} успешно получена", id);
        return propertyMapper.toDto(property);
    }

    @Override
    public List<PropertyDto> getByUserId(Integer id) {
        List<Property> properties = propertyDao.findByUserId(id);
        log.info("Найдено {} недвижимости для пользователя с ID: {}", properties.size(), id);
        return properties.stream()
                .map(propertyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PropertyDto> getAll() {
        List<Property> properties = propertyDao.findAll();
        properties.forEach(Property::loadLazyFields);
        log.info("Найдено {} недвижимости", properties.size());
        return properties.stream()
                .map(propertyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateById(Integer id, PropertyDto propertyDto) {
        Property property = propertyDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        propertyDto.setId(id);
        propertyMapper.updateEntity(propertyDto, property);
        propertyDao.update(property);
        log.info("Недвижимость с ID: {} успешно обновлена", id);
    }

    @Override
    public void deleteById(Integer id) {
        Property property = propertyDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        propertyDao.delete(property);
        log.info("Недвижимость с ID: {} успешно удалена", id);
    }

    @Override
    public List<PropertyDto> getAllWithEssentialDetails() {
        List<Property> properties = propertyDao.findAllWithEssentialDetails();
        log.info("Найдено {} недвижимости с основными деталями", properties.size());
        return properties.stream()
                .map(propertyMapper::toDto)
                .collect(Collectors.toList());
    }
}
