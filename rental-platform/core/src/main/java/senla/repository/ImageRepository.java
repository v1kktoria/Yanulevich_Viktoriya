package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senla.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
