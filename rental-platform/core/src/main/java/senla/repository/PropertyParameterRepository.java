package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import senla.model.PropertyParameter;
import senla.model.id.PropertyParameterId;

import java.util.Optional;

@Repository
public interface PropertyParameterRepository extends JpaRepository<PropertyParameter, Integer> {

    Optional<PropertyParameter> findById(PropertyParameterId id);
}
