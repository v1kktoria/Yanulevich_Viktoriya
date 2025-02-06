package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senla.model.PropertyParameter;
import senla.model.id.PropertyParameterId;

import java.util.Optional;

public interface PropertyParameterRepository extends JpaRepository<PropertyParameter, Integer> {

    Optional<PropertyParameter> findById(PropertyParameterId id);
}
