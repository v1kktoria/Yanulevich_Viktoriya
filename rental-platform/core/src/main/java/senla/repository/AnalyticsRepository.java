package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senla.model.Analytics;

import java.util.List;

public interface AnalyticsRepository extends JpaRepository<Analytics, Integer> {

    List<Analytics> findAllByPropertyId(Integer id);
}
