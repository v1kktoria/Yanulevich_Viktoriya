package senla.service;

import senla.model.Analytics;

import java.util.List;
import java.util.Optional;

public interface AnalyticsService {

    Optional<Analytics> create(Analytics analytics);

    Optional<Analytics> getById(Integer id);

    List<Analytics> getByPropertyId(Integer id);

    List<Analytics> getAll();

    void updateById(Integer id, Analytics analytics);

    void deleteById(Integer id);
}
