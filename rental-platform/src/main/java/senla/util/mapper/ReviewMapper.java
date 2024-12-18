package senla.util.mapper;

import jakarta.servlet.http.HttpServletRequest;
import senla.model.Property;
import senla.model.Review;
import senla.model.User;
import senla.service.PropertyService;
import senla.service.UserService;

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

    public static Review fromRequest(HttpServletRequest request, PropertyService propertyService, UserService userService) {
        Integer propertyId = Integer.parseInt(request.getParameter("property_id"));
        Integer userId = Integer.parseInt(request.getParameter("user_id"));
        Integer rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");

        return Review.builder()
                .property(propertyService.getById(propertyId))
                .user(userService.getById(userId))
                .rating(rating)
                .comment(comment)
                .createdAt(new java.sql.Timestamp(System.currentTimeMillis()))
                .deleted(false)
                .build();
    }
}
