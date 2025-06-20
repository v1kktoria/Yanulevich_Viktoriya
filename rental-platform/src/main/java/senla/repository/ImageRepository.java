package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senla.model.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    List<Image> findByPropertyId(Integer id);
}
