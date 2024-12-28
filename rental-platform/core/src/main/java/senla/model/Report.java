package senla.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import senla.model.constant.ReportType;
import senla.model.constant.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class Report {
    private int id;
    private User user;
    private ReportType type;
    private int contentId;
    private String message;
    private Status status;
    private LocalDateTime createdAt;
    private boolean deleted;
}
