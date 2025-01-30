package senla.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import senla.model.id.PropertyParameterId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyParameterDto {

    private PropertyParameterId id;

    private Integer propertyId;

    private Integer parameterId;

    private String value;

}
