package senla.service;

import senla.dto.AnalyticsDto;

import java.util.List;

public interface AnalyticsService {

    AnalyticsDto create(AnalyticsDto analyticsDto);

    AnalyticsDto getById(Integer id);

    List<AnalyticsDto> getByPropertyId(Integer id);

    List<AnalyticsDto> getAll();

    void updateById(Integer id, AnalyticsDto analyticsDto);

    void deleteById(Integer id);
}
