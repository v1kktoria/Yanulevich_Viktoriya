package senla.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Role {
    private int id;
    private String roleName;
    private String description;

    private Role(Builder builder) {
        this.id = builder.id;
        this.roleName = builder.roleName;
        this.description = builder.description;
    }

    public static class Builder {
        private int id;
        private String roleName;
        private String description;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setRoleName(String roleName) {
            this.roleName = roleName;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Role build() {
            return new Role(this);
        }
    }
}
