package senla.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Profile {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private LocalDateTime registrationDate;
    private User user;

    private Profile(Builder builder) {
        this.id = builder.id;
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.email = builder.email;
        this.phone = builder.phone;
        this.registrationDate = builder.registrationDate;
        this.user = builder.user;
    }


    public static class Builder {
        private int id;
        private String firstname;
        private String lastname;
        private String email;
        private String phone;
        private LocalDateTime registrationDate;
        private User user;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setFirstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder setLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setRegistrationDate(LocalDateTime registrationDate) {
            this.registrationDate = registrationDate;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Profile build() {
            return new Profile(this);
        }
    }
}
