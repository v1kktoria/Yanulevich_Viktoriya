package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import senla.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
}
