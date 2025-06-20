package senla.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private Integer id;

    private Integer propertyId;

    @NotBlank(message = "Страна не может быть пустой")
    private String country;

    @NotBlank(message = "Город не может быть пустым")
    private String city;

    @NotBlank(message = "Улица не может быть пустой")
    private String street;

    @NotBlank(message = "Номер дома не может быть пустым")
    private String houseNumber;

}
