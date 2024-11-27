package senla.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Favorite {
    private int id;
    private User user;
    private Property property;

    private Favorite(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.property = builder.property;
    }


    public static class Builder {
        private int id;
        private User user;
        private Property property;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setProperty(Property property) {
            this.property = property;
            return this;
        }

        public Favorite build() {
            return new Favorite(this);
        }
    }
}
