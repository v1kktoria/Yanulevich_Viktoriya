package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senla.model.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findByChatId(Integer chatId);
}
