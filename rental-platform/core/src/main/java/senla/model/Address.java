package senla.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(name = "country")
    @NotBlank(message = "Страна не может быть пустой")
    private String country;

    @Column(name = "city")
    @NotBlank(message = "Город не может быть пустым")
    private String city;

    @Column(name = "street")
    @NotBlank(message = "Улица не может быть пустой")
    private String street;

    @Column(name = "house_number")
    @NotBlank(message = "Номер дома не может быть пустым")
    private String houseNumber;

}
