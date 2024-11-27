package senla.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import senla.model.ENUM.PropertyType;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Property {
    private int id;
    private User owner;
    private PropertyType type;
    private double area;
    private double price;
    private int rooms;
    private String description;
    private LocalDateTime createdAt;
    private boolean deleted;

    private Property(Builder builder) {
        this.id = builder.id;
        this.owner = builder.owner;
        this.type = builder.type;
        this.area = builder.area;
        this.price = builder.price;
        this.rooms = builder.rooms;
        this.description = builder.description;
        this.createdAt = builder.createdAt;
        this.deleted = builder.deleted;
    }

    public static class Builder {
        private int id;
        private User owner;
        private PropertyType type;
        private double area;
        private double price;
        private int rooms;
        private String description;
        private LocalDateTime createdAt;
        private boolean deleted;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setOwner(User owner) {
            this.owner = owner;
            return this;
        }

        public Builder setType(PropertyType type) {
            this.type = type;
            return this;
        }

        public Builder setArea(double area) {
            this.area = area;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setRooms(int rooms) {
            this.rooms = rooms;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setDeleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public Property build() {
            return new Property(this);
        }
    }
}
