package senla.model;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@jakarta.persistence.Entity
@Table(name = "analytics")
public class Analytics extends Entity{
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
