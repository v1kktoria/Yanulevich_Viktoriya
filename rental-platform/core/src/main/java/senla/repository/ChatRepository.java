package senla.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import senla.model.Chat;
import senla.model.User;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

    @Query("SELECT c FROM Chat c JOIN c.users u WHERE u.id = :userId")
    List<Chat> findByUserId(@Param("userId") Integer userId);
}
