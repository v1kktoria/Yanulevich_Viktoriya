package senla.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsDto {

    private Integer id;

    private Integer propertyId;

    @Min(value = 0, message = "Количество просмотров не может быть меньше нуля")
    private int views;

    @Min(value = 0, message = "Количество заявок не может быть меньше нуля")
    private int applicationsCount;

    private LocalDateTime createdAt;

}
