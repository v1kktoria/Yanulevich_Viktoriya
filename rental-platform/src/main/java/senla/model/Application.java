package senla.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import senla.model.constant.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class Application {
    private int id;
    private Property property;
    private User tenant;
    private String message;
    private Status status;
    private LocalDateTime createdAt;
    private boolean deleted;
}
