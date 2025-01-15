package senla.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import senla.model.constant.ReportType;
import senla.model.constant.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reports")
public class Report extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "type")
    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private ReportType type;

    @Column(name = "content_id")
    private int contentId;

    @Column(name = "message")
    private String message;

    @Column(name = "status")
    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private Status status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted")
    private boolean deleted;

    public void loadLazyFields() {
        user.getUsername();
    }
}
