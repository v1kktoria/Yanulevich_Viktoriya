package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senla.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
