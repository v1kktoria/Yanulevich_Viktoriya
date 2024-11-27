package senla.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Review {
    private int id;
    private Property property;
    private User user;
    private int rating;
    private String comment;
    private Timestamp createdAt;
    private boolean deleted;

    private Review(Builder builder) {
        this.id = builder.id;
        this.property = builder.property;
        this.user = builder.user;
        this.rating = builder.rating;
        this.comment = builder.comment;
        this.createdAt = builder.createdAt;
        this.deleted = builder.deleted;
    }

    public static class Builder {
        private int id;
        private Property property;
        private User user;
        private int rating;
        private String comment;
        private Timestamp createdAt;
        private boolean deleted;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setProperty(Property property) {
            this.property = property;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setRating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder setCreatedAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setDeleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }
}
