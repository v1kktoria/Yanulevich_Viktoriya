package service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import senla.Main;
import senla.dto.AnalyticsDto;
import senla.exception.ServiceException;
import senla.service.AnalyticsService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = Main.class)
@RequiredArgsConstructor
@Transactional
@ActiveProfiles("test")
public class AnalyticsServiceIntegrationTest {

    @Autowired
    private AnalyticsService analyticsService;

    private AnalyticsDto analyticsDto;

    @BeforeEach
    public void setUp() {
        analyticsDto = new AnalyticsDto();
        analyticsDto.setPropertyId(1);
        analyticsDto.setViews(10);
    }

    @Test
    public void testCreateAnalytics() {
        AnalyticsDto createdAnalytics = analyticsService.create(analyticsDto);
        assertThat(createdAnalytics).isNotNull();
        assertThat(createdAnalytics.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetAnalyticsById() {
        AnalyticsDto createdAnalytics = analyticsService.create(analyticsDto);
        AnalyticsDto fetchedAnalytics = analyticsService.getById(createdAnalytics.getId());
        assertThat(fetchedAnalytics).isNotNull();
        assertThat(fetchedAnalytics.getId()).isEqualTo(createdAnalytics.getId());
    }

    @Test
    public void testGetAnalyticsByIdNotFound() {
        assertThrows(ServiceException.class, () -> analyticsService.getById(9999));
    }

    @Test
    public void testGetAllAnalytics() {
        analyticsService.create(analyticsDto);
        assertThat(analyticsService.getAll()).isNotEmpty();
    }

    @Test
    public void testGetAnalyticsByPropertyId() {
        analyticsService.create(analyticsDto);
        assertThat(analyticsService.getByPropertyId(1)).isNotEmpty();
    }

    @Test
    public void testUpdateAnalytics() {
        AnalyticsDto createdAnalytics = analyticsService.create(analyticsDto);
        createdAnalytics.setViews(100);
        analyticsService.updateById(createdAnalytics.getId(), createdAnalytics);

        AnalyticsDto updatedAnalytics = analyticsService.getById(createdAnalytics.getId());
        assertThat(updatedAnalytics.getViews()).isEqualTo(100);
    }

    @Test
    public void testDeleteAnalytics() {
        AnalyticsDto createdAnalytics = analyticsService.create(analyticsDto);
        analyticsService.deleteById(createdAnalytics.getId());

        assertThrows(ServiceException.class, () -> analyticsService.getById(createdAnalytics.getId()));
    }
}