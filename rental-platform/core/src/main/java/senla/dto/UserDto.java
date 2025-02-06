package senla.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;

    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 8, message = "Пароль должен быть не меньше 8 символов")
    private String password;

    private Set<RoleDto> roles;

    private Integer profileId;

    private Set<PropertyDto> properties;

    private Set<ApplicationDto> applications;

    private Integer favoriteId;

    private Set<ReportDto> reports;

    private Set<ReviewDto> reviews;

}
