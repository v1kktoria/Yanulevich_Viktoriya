package senla.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import senla.model.constant.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDto {

    private Integer id;

    private Integer propertyId;

    private Integer tenantId;

    private Integer ownerId;

    @Size(max = 500, message = "Сообщение не может быть длиннее 500 символов")
    private String message;

    private Status status;

    private LocalDateTime createdAt;

}
