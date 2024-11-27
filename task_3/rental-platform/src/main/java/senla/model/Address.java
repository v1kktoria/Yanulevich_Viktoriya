package senla.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Address {
    private int id;
    private Property property;
    private String country;
    private String city;
    private String street;
    private String houseNumber;

    private Address(Builder builder) {
        this.id = builder.id;
        this.property = builder.property;
        this.country = builder.country;
        this.city = builder.city;
        this.street = builder.street;
        this.houseNumber = builder.houseNumber;
    }

    public static class Builder {
        private int id;
        private Property property;
        private String country;
        private String city;
        private String street;
        private String houseNumber;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setProperty(Property property) {
            this.property = property;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setStreet(String street) {
            this.street = street;
            return this;
        }

        public Builder setHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
