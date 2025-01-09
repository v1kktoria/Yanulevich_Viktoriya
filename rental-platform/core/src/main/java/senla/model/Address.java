package senla.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class Address {
    private int id;
    private Property property;
    private String country;
    private String city;
    private String street;
    private String houseNumber;

}
