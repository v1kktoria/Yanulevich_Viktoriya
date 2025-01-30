package senla.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import senla.model.constant.ReportType;
import senla.model.constant.Status;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {

    private Integer id;

    private Integer userId;

    private ReportType type;

    private int contentId;

    @Size(max = 500, message = "Комментарий не может превышать 500 символов")
    private String message;

    private Status status;

}
