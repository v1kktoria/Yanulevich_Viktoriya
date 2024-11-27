package senla.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Parameter {
    private int id;
    private String name;
    private String description;

    private Parameter(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
    }


    public static class Builder {
        private int id;
        private String name;
        private String description;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Parameter build() {
            return new Parameter(this);
        }
    }
}
