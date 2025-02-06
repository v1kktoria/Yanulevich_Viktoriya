package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senla.model.Favorite;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

    List<Favorite> findAllByUserId(Integer id);
}
