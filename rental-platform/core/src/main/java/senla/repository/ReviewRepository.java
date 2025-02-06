package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import senla.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
