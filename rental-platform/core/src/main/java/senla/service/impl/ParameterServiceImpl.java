package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dao.ParameterDao;
import senla.dto.ParameterDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Parameter;
import senla.service.ParameterService;
import senla.util.mappers.ParameterMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@MeasureExecutionTime
public class ParameterServiceImpl implements ParameterService {

    private final ParameterDao parameterDao;

    private final ParameterMapper parameterMapper;

    @Override
    public ParameterDto create(ParameterDto parameterDto) {
        Parameter parameter = parameterMapper.toEntity(parameterDto);
        ParameterDto createdParameter = parameterMapper.toDto(parameterDao.save(parameter));
        log.info("Параметр успешно добавлен с ID: {}", createdParameter.getId());
        return createdParameter;
    }

    @Override
    public ParameterDto getById(Integer id) {
        ParameterDto parameter = parameterMapper.toDto(parameterDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id)));
        log.info("Параметр успешно получен с ID: {}", parameter.getId());
        return parameter;
    }

    @Override
    public List<ParameterDto> getAll() {
        List<Parameter> parameters = parameterDao.findAll();
        List<ParameterDto> parameterDtos = parameters.stream()
                .map(parameterMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} параметров", parameterDtos.size());
        return parameterDtos;
    }

    @Override
    public void updateById(Integer id, ParameterDto parameterDto) {
        Parameter parameter = parameterDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        parameterDto.setId(id);
        parameterMapper.updateEntity(parameterDto, parameter);
        parameterDao.update(parameter);
        log.info("Параметр с ID: {} успешно обновлен", id);
    }

    @Override
    public void deleteById(Integer id) {
        Parameter parameter = parameterDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        parameterDao.delete(parameter);
        log.info("Параметр с ID: {} успешно удален", id);
    }
}
