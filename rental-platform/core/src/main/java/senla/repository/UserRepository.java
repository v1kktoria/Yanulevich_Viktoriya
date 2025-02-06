package senla.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import senla.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @EntityGraph(value = "user-roles-properties-applications", type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT u FROM User u")
    List<User> findAllWithEssentialDetails();
}
