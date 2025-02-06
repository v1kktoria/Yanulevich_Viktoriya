package senla.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {

    private Integer id;

    private Integer propertyId;

    @NotBlank(message = "Путь к файлу не может быть пустым")
    private String filepath;

}
