package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dao.AnalyticsDao;
import senla.dao.PropertyDao;
import senla.dto.AnalyticsDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Analytics;
import senla.model.Property;
import senla.service.AnalyticsService;
import senla.util.mappers.AnalyticsMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@MeasureExecutionTime
public class AnalyticsServiceImpl implements AnalyticsService {

    private final AnalyticsDao analyticsDao;

    private final PropertyDao propertyDao;

    private final AnalyticsMapper analyticsMapper;

    @Override
    public AnalyticsDto create(AnalyticsDto analyticsDto) {
        Property property = propertyDao.findById(analyticsDto.getPropertyId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, analyticsDto.getPropertyId()));

        Analytics analytics = analyticsMapper.toEntity(analyticsDto, property);
        AnalyticsDto createdAnalytics = analyticsMapper.toDto(analyticsDao.save(analytics));
        log.info("Аналитика успешно создана с ID: {}", createdAnalytics.getId());
        return createdAnalytics;
    }

    @Override
    public AnalyticsDto getById(Integer id) {
        AnalyticsDto analytics = analyticsMapper.toDto(analyticsDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id)));
        log.info("Аналитика успешно получена: {}", analytics);
        return analytics;
    }

    @Override
    public List<AnalyticsDto> getByPropertyId(Integer id) {
        List<Analytics> analytics = analyticsDao.findByPropertyId(id);
        List<AnalyticsDto> analyticsDtos = analytics.stream()
                .map(analyticsMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} аналитик для недвижимости с ID: {}", analyticsDtos.size(), id);
        return analyticsDtos;
    }

    @Override
    public List<AnalyticsDto> getAll() {
        List<Analytics> analytics = analyticsDao.findAll();
        List<AnalyticsDto> analyticsDtos = analytics.stream()
                .map(analyticsMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} аналитик", analyticsDtos.size());
        return analyticsDtos;
    }

    @Override
    public void updateById(Integer id, AnalyticsDto analyticsDto) {
        Analytics analytics = analyticsDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        analyticsDto.setId(id);
        analyticsMapper.updateEntity(analyticsDto, analytics);
        analyticsDao.update(analytics);
        log.info("Аналитика с ID: {} успешно обновлена", id);
    }

    @Override
    public void deleteById(Integer id) {
        Analytics analytics = analyticsDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        analyticsDao.delete(analytics);
        log.info("Аналитика с ID: {} успешно удалена", id);
    }
}
