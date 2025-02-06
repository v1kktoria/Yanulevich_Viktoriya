package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import senla.model.Parameter;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Integer> {
}
