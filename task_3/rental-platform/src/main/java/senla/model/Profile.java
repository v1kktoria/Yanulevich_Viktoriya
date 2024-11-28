package senla.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class Profile {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private LocalDateTime registrationDate;
    private User user;
}
