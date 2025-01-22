package senla.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "images")
public class Image extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(name = "filepath")
    @NotBlank(message = "Путь к файлу не может быть пустым")
    private String filepath;
}
