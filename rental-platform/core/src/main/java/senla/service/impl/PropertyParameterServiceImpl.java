package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dao.PropertyParameterDao;
import senla.dto.ParameterDto;
import senla.dto.PropertyDto;
import senla.dto.PropertyParameterDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.PropertyParameter;
import senla.model.id.PropertyParameterId;
import senla.service.PropertyParameterService;
import senla.util.mappers.PropertyParameterMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@MeasureExecutionTime
public class PropertyParameterServiceImpl implements PropertyParameterService {

    private final PropertyParameterDao propertyParameterDao;

    private final PropertyParameterMapper propertyParameterMapper;

    @Override
    public void create(PropertyParameterDto propertyParameterDto) {
        PropertyParameter propertyParameter = propertyParameterMapper.toEntity(propertyParameterDto);
        propertyParameterDao.save(propertyParameter);
        log.info("PropertyParameter с ID: {} успешно создан", propertyParameter.getId());
    }

    @Override
    public PropertyParameterDto getByPropertyAndParameter(PropertyDto propertyDto, ParameterDto parameterDto) {

        PropertyParameterId id = PropertyParameterId.builder()
                .property_id(propertyDto.getId())
                .parameter_id(parameterDto.getId()).build();

        PropertyParameter propertyParameter = propertyParameterDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        PropertyParameterDto propertyParameterDto = propertyParameterMapper.toDto(propertyParameter);
        log.info("PropertyParameter с ID: {} успешно получен", id);
        return propertyParameterDto;
    }

    @Override
    public List<PropertyParameterDto> getAll() {
        List<PropertyParameter> propertyParameters = propertyParameterDao.findAll();
        List<PropertyParameterDto> propertyParameterDtos = propertyParameters.stream()
                .map(propertyParameterMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} PropertyParameters", propertyParameterDtos.size());
        return propertyParameterDtos;
    }

    @Override
    public void deleteByPropertyAndParameter(PropertyDto propertyDto, ParameterDto parameterDto) {

        PropertyParameterId id = PropertyParameterId.builder()
                .property_id(propertyDto.getId())
                .parameter_id(parameterDto.getId()).build();

        PropertyParameter propertyParameter = propertyParameterDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        propertyParameterDao.delete(propertyParameter);
        log.info("PropertyParameter с ID: {} успешно удален", id);
    }
}
