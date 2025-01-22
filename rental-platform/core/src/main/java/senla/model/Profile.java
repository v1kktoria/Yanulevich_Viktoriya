package senla.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profiles")
public class Profile extends BaseEntity {

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 1, max = 50, message = "Имя должно быть от 1 до 50 символов")
    @Column(name = "first_name")
    private String firstname;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(min = 1, max = 50, message = "Фамилия должна быть от 1 до 50 символов")
    @Column(name = "last_name")
    private String lastname;

    @Email(message = "Некорректный email")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Телефон не может быть пустым")
    @Column(name = "phone")
    private String phone;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
