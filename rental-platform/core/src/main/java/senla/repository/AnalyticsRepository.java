package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import senla.model.Analytics;

import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics, Integer> {

    List<Analytics> findAllByPropertyId(Integer id);
}
