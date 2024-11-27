package senla.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Analytics {
    private int id;
    private Property property;
    private int views;
    private int applicationsCount;
    private LocalDateTime createdAt;

    private Analytics(Builder builder) {
        this.id = builder.id;
        this.property = builder.property;
        this.views = builder.views;
        this.applicationsCount = builder.applicationsCount;
        this.createdAt = builder.createdAt;
    }

    public static class Builder {
        private int id;
        private Property property;
        private int views;
        private int applicationsCount;
        private LocalDateTime createdAt;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setProperty(Property property) {
            this.property = property;
            return this;
        }

        public Builder setViews(int views) {
            this.views = views;
            return this;
        }

        public Builder setApplicationsCount(int applicationsCount) {
            this.applicationsCount = applicationsCount;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Analytics build() {
            return new Analytics(this);
        }
    }
}
