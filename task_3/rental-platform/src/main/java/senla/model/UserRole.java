package senla.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserRole {
    private User user;
    private Role role;

    private UserRole(Builder builder) {
        this.user = builder.user;
        this.role = builder.role;
    }

    public static class Builder {
        private User user;
        private Role role;

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setRole(Role role) {
            this.role = role;
            return this;
        }

        public UserRole build() {
            return new UserRole(this);
        }
    }
}
