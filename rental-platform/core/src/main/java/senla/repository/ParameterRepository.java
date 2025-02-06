package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senla.model.Parameter;

public interface ParameterRepository extends JpaRepository<Parameter, Integer> {
}
