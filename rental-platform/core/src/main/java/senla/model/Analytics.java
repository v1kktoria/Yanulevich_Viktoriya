package senla.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "analytics")
public class Analytics extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(name = "views")
    @Min(value = 0, message = "Количество просмотров не может быть меньше нуля")
    private int views;

    @Column(name = "applications_count")
    @Min(value = 0, message = "Количество заявок не может быть меньше нуля")
    private int applicationsCount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
