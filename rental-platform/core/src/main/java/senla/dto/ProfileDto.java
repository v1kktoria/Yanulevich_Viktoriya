package senla.dto;

import jakarta.validation.constraints.Email;
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
public class ProfileDto {

    private Integer id;

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 1, max = 50, message = "Имя должно быть от 1 до 50 символов")
    private String firstname;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(min = 1, max = 50, message = "Фамилия должна быть от 1 до 50 символов")
    private String lastname;

    @Email(message = "Некорректный email")
    private String email;

    @NotBlank(message = "Телефон не может быть пустым")
    private String phone;

    private Integer userId;

}
