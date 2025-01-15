package senla.model.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyParameterId implements Serializable {
    @Column(insertable = false, updatable = false)
    private Integer property_id;

    @Column(insertable = false, updatable = false)
    private Integer parameter_id;
}
