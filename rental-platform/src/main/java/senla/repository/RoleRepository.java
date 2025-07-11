package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senla.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleName(String name);
}
