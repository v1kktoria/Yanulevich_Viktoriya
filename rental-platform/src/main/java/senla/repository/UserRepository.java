package senla.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import senla.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @EntityGraph(value = "user-roles", type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsernameWithRoles(String username);

    @Override
    @EntityGraph(value = "user-roles", type = EntityGraph.EntityGraphType.FETCH)
    Optional<User> findById(Integer integer);

    @Override
    @EntityGraph(value = "user-roles", type = EntityGraph.EntityGraphType.FETCH)
    List<User> findAll();
}
