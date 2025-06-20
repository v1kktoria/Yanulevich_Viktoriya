package senla.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    private Integer id;

    private Integer chatId;

    private Integer senderId;

    @Size(max = 500, message = "Сообщение не может быть длиннее 500 символов")
    private String content;

    private LocalDateTime createdAt;
}
