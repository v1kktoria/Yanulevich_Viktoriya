package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import senla.model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
}
