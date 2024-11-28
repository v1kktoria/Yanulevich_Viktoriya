package senla.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class Analytics {
    private int id;
    private Property property;
    private int views;
    private int applicationsCount;
    private LocalDateTime createdAt;
}
