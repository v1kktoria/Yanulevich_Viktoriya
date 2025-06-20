package senla.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import senla.model.Favorite;

import java.util.List;
import java.util.Optional;


public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

    @EntityGraph(value = "favorite-properties", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Favorite> findByUserId(Integer userId);
}
