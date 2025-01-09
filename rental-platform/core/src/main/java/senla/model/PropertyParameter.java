package senla.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import senla.model.id.PropertyParameterId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@jakarta.persistence.Entity
@Table(name = "properties_parameters")
public class PropertyParameter implements Identifiable<PropertyParameterId>{
    @EmbeddedId
    private PropertyParameterId id;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "parameter_id")
    private Parameter parameter;

    @Column(name = "value")
    private String value;
}
