package senla.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private Integer id;

    @NotBlank(message = "Название роли не может быть пустым")
    private String roleName;

    @Size(max = 255, message = "Описание роли не должно превышать 255 символов")
    private String description;

}
