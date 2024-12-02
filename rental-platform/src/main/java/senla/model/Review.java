package senla.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class Review {
    private int id;
    private Property property;
    private User user;
    private int rating;
    private String comment;
    private Timestamp createdAt;
    private boolean deleted;
}
