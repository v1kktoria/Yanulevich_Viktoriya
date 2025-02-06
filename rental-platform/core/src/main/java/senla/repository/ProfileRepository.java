package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import senla.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
