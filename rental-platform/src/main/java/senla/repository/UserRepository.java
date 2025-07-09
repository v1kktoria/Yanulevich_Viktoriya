package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senla.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByKeycloakId(String keycloakId);

    Optional<User> findByKeycloakId(String keycloakId);
}
