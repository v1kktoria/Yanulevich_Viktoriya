package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senla.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
