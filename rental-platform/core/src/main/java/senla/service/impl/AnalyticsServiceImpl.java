package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dto.AnalyticsDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Analytics;
import senla.model.Property;
import senla.repository.AnalyticsRepository;
import senla.repository.PropertyRepository;
import senla.service.AnalyticsService;
import senla.util.mappers.AnalyticsMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class AnalyticsServiceImpl implements AnalyticsService {

    private final AnalyticsRepository analyticsRepository;

    private final PropertyRepository propertyRepository;

    private final AnalyticsMapper analyticsMapper;

    @Transactional
    @Override
    public AnalyticsDto create(AnalyticsDto analyticsDto) {
        Property property = propertyRepository.findById(analyticsDto.getPropertyId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, analyticsDto.getPropertyId()));

        Analytics analytics = analyticsMapper.toEntity(analyticsDto, property);
        AnalyticsDto createdAnalytics = analyticsMapper.toDto(analyticsRepository.save(analytics));
        log.info("Аналитика успешно создана с ID: {}", createdAnalytics.getId());
        return createdAnalytics;
    }

    @Override
    public AnalyticsDto getById(Integer id) {
        AnalyticsDto analytics = analyticsMapper.toDto(analyticsRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id)));
        log.info("Аналитика успешно получена: {}", analytics);
        return analytics;
    }

    @Override
    public List<AnalyticsDto> getByPropertyId(Integer id) {
        List<Analytics> analytics = analyticsRepository.findAllByPropertyId(id);
        List<AnalyticsDto> analyticsDtos = analytics.stream()
                .map(analyticsMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} аналитик для недвижимости с ID: {}", analyticsDtos.size(), id);
        return analyticsDtos;
    }

    @Override
    public List<AnalyticsDto> getAll() {
        List<Analytics> analytics = analyticsRepository.findAll();
        List<AnalyticsDto> analyticsDtos = analytics.stream()
                .map(analyticsMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} аналитик", analyticsDtos.size());
        return analyticsDtos;
    }

    @Transactional
    @Override
    public void updateById(Integer id, AnalyticsDto analyticsDto) {
        Analytics analytics = analyticsRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        analyticsDto.setId(id);
        analyticsMapper.updateEntity(analyticsDto, analytics);
        analyticsRepository.save(analytics);
        log.info("Аналитика с ID: {} успешно обновлена", id);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        Analytics analytics = analyticsRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        analyticsRepository.delete(analytics);
        log.info("Аналитика с ID: {} успешно удалена", id);
    }
}
