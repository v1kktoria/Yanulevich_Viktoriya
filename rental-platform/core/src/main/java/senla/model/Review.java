package senla.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Min(value = 1, message = "Рейтинг не может быть меньше 1")
    @Max(value = 5, message = "Рейтинг не может быть больше 5")
    @Column(name = "rating")
    private int rating;

    @Size(max = 500, message = "Комментарий не может превышать 500 символов")
    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "deleted")
    private boolean deleted;

    public void loadLazyFields() {
        user.getUsername();
    }
}
