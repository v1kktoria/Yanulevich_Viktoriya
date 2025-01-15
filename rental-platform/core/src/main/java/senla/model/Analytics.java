package senla.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
    private int views;

    @Column(name = "applications_count")
    private int applicationsCount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
