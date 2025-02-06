package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import senla.model.Application;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    List<Application> findAllByPropertyId(Integer id);
}
