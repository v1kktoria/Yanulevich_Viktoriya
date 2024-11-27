package senla.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Image {
    private int id;
    private Property property;
    private String filepath;

    private Image(Builder builder) {
        this.id = builder.id;
        this.property = builder.property;
        this.filepath = builder.filepath;
    }


    public static class Builder {
        private int id;
        private Property property;
        private String filepath;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setProperty(Property property) {
            this.property = property;
            return this;
        }

        public Builder setFilepath(String filepath) {
            this.filepath = filepath;
            return this;
        }

        public Image build() {
            return new Image(this);
        }
    }
}
