package senla.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parameters")
public class Parameter extends BaseEntity {

    @Column(name = "name")
    @NotBlank(message = "Имя параметра не может быть пустым")
    @Size(min = 3, max = 100, message = "Имя параметра должно быть от 3 до 100 символов")
    private String name;

    @Size(max = 500, message = "Описание не должно превышать 500 символов")
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "parameter", cascade = CascadeType.REMOVE)
    private Set<PropertyParameter> propertyParameters = new HashSet<>();
}
