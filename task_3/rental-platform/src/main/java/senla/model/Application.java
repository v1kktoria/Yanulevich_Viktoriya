package senla.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import senla.model.ENUM.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Application {
    private int id;
    private Property property;
    private User tenant;
    private String message;
    private Status status;
    private LocalDateTime createdAt;
    private boolean deleted;

    private Application(Builder builder) {
        this.id = builder.id;
        this.property = builder.property;
        this.tenant = builder.tenant;
        this.message = builder.message;
        this.status = builder.status;
        this.createdAt = builder.createdAt;
        this.deleted = builder.deleted;
    }

    public static class Builder {
        private int id;
        private Property property;
        private User tenant;
        private String message;
        private Status status;
        private LocalDateTime createdAt;
        private boolean deleted;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setProperty(Property property) {
            this.property = property;
            return this;
        }

        public Builder setTenant(User tenant) {
            this.tenant = tenant;
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

        public Application build() {
            return new Application(this);
        }
    }
}
