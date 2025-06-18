package senla.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import senla.model.constant.PropertyType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDto {

    private Integer id;

    private Integer ownerId;

    private PropertyType type;

    @Min(value = 0, message = "Площадь не может быть меньше нуля")
    private double area;

    @Min(value = 0, message = "Цена не может быть меньше нуля")
    private double price;

    @Min(value = 0, message = "Количество комнат не может быть меньше нуля")
    private int rooms;

    @Size(max = 500, message = "Описание не должно превышать 500 символов")
    private String description;

    private double rating;

    private LocalDateTime createdAt;

    private List<ParameterDto> parameters;

    private AddressDto address;

    private List<ImageDto> images;

    private List<ReviewDto> reviews;
}
