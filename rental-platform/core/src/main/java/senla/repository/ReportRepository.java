package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senla.model.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {
}
