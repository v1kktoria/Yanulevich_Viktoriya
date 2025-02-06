package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senla.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
