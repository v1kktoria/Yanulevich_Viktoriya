package senla.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PropertyParameter {
    private Property property;
    private Parameter parameter;
    private String value;

    private PropertyParameter(Builder builder) {
        this.property = builder.property;
        this.parameter = builder.parameter;
        this.value = builder.value;
    }

    public static class Builder {
        private Property property;
        private Parameter parameter;
        private String value;

        public Builder setProperty(Property property) {
            this.property = property;
            return this;
        }

        public Builder setParameter(Parameter parameter) {
            this.parameter = parameter;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public PropertyParameter build() {
            return new PropertyParameter(this);
        }
    }
}
