package senla.model;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@jakarta.persistence.Entity
@Table(name = "images")
public class Image extends Entity{
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(name = "filepath")
    private String filepath;
}
