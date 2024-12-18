package senla.service;

import senla.model.Analytics;

import java.util.List;

public interface AnalyticsService {

    Analytics create(Analytics analytics);

    Analytics getById(Integer id);

    Analytics getByPropertyId(Integer id);

    List<Analytics> getAll();

    void updateById(Integer id, Analytics analytics);

    void deleteById(Integer id);
}
