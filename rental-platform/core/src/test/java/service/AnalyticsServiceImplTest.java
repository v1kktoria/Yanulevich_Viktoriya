package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import senla.dto.AnalyticsDto;
import senla.exception.ServiceException;
import senla.model.Analytics;
import senla.model.Property;
import senla.repository.AnalyticsRepository;
import senla.repository.PropertyRepository;
import senla.service.impl.AnalyticsServiceImpl;
import senla.util.mappers.AnalyticsMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AnalyticsServiceImplTest {

    @Mock
    private AnalyticsRepository analyticsRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private AnalyticsMapper analyticsMapper;

    @InjectMocks
    private AnalyticsServiceImpl analyticsService;

    private AnalyticsDto analyticsDto;

    private Property property;

    private Analytics analytics;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        analyticsDto = new AnalyticsDto();
        analyticsDto.setId(1);
        analyticsDto.setPropertyId(1);

        property = new Property();
        property.setId(1);

        analytics = new Analytics();
        analytics.setId(1);
    }

    @Test
    void testCreate() {
        when(propertyRepository.findById(1)).thenReturn(Optional.of(property));
        when(analyticsMapper.toEntity(analyticsDto, property)).thenReturn(analytics);
        when(analyticsRepository.save(analytics)).thenReturn(analytics);
        when(analyticsMapper.toDto(analytics)).thenReturn(analyticsDto);

        AnalyticsDto createdAnalytics = analyticsService.create(analyticsDto);

        assertNotNull(createdAnalytics);
        assertEquals(1, createdAnalytics.getId());
        verify(analyticsRepository, times(1)).save(analytics);
    }

    @Test
    void testCreatePropertyNotFound() {
        when(propertyRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> analyticsService.create(analyticsDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
        verify(analyticsRepository, never()).save(any());
    }

    @Test
    void testGetById() {
        when(analyticsRepository.findById(1)).thenReturn(Optional.of(analytics));
        when(analyticsMapper.toDto(analytics)).thenReturn(analyticsDto);

        AnalyticsDto result = analyticsService.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        when(analyticsRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> analyticsService.getById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testUpdateById() {
        when(analyticsRepository.findById(1)).thenReturn(Optional.of(analytics));

        analyticsService.updateById(1, analyticsDto);

        verify(analyticsMapper, times(1)).updateEntity(analyticsDto, analytics);
        verify(analyticsRepository, times(1)).save(analytics);
    }

    @Test
    void testUpdateByIdNotFound() {
        when(analyticsRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> analyticsService.updateById(1, analyticsDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        when(analyticsRepository.findById(1)).thenReturn(Optional.of(analytics));

        analyticsService.deleteById(1);

        verify(analyticsRepository, times(1)).delete(analytics);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(analyticsRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> analyticsService.deleteById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }
}
