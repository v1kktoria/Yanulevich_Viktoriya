package senla.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import senla.model.Property;
import senla.model.constant.PropertyType;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Integer> {

    @EntityGraph(value = "property-parameters-reviews-images", type = EntityGraph.EntityGraphType.FETCH)
    List<Property> findAllByOwnerId(Integer id, Sort sort);

    @Query("SELECT DISTINCT p FROM Property p WHERE " +
            "(:type IS NULL OR p.type = :type) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:minRooms IS NULL OR p.rooms >= :minRooms) AND " +
            "(:maxRooms IS NULL OR p.rooms <= :maxRooms) AND " +
            "(:description IS NULL OR LOWER(p.description) LIKE LOWER(CONCAT('%', :description, '%') ) )")
    List<Property> searchProperties(PropertyType type, Double minPrice, Double maxPrice,
                                    Integer minRooms, Integer maxRooms, String description, Sort sort);

    @Override
    @EntityGraph(value = "property-parameters-reviews-images", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Property> findById(Integer id);
}
