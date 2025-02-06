package senla.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import senla.model.Property;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

    List<Property> findAllByOwnerId(Integer id);

    @EntityGraph(value = "property-owner-address-images", type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT p FROM Property p")
    List<Property> findAllWithEssentialDetails();
}
