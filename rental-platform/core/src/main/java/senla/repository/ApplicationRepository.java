package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senla.model.Application;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    List<Application> findAllByPropertyId(Integer id);
}
