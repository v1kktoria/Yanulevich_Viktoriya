package senla.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import senla.model.ENUM.ReportType;
import senla.model.ENUM.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Report {
    private int id;
    private User user;
    private ReportType type;
    private int contentId;
    private String message;
    private Status status;
    private LocalDateTime createdAt;
    private boolean deleted;

    private Report(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.type = builder.type;
        this.contentId = builder.contentId;
        this.message = builder.message;
        this.status = builder.status;
        this.createdAt = builder.createdAt;
        this.deleted = builder.deleted;
    }

    public static class Builder {
        private int id;
        private User user;
        private ReportType type;
        private int contentId;
        private String message;
        private Status status;
        private LocalDateTime createdAt;
        private boolean deleted;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setType(ReportType type) {
            this.type = type;
            return this;
        }

        public Builder setContentId(int contentId) {
            this.contentId = contentId;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setDeleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public Report build() {
            return new Report(this);
        }
    }
}
