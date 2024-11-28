package senla.util.mapper;

import senla.model.Property;
import senla.model.Review;
import senla.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewMapper {
    public static Review mapRow(ResultSet resultSet) throws SQLException {
        Property property = PropertyMapper.mapRow(resultSet);
        User user = UserMapper.mapRow(resultSet, "review_user_");

        return Review.builder()
                .id(resultSet.getInt("id"))
                .property(property)
                .user(user)
                .rating(resultSet.getInt("rating"))
                .comment(resultSet.getString("comment"))
                .createdAt(resultSet.getTimestamp("created_at"))
                .deleted(resultSet.getBoolean("deleted"))
                .build();
    }
}
