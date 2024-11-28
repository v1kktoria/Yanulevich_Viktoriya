package senla.util.mapper;

import senla.model.Favorite;
import senla.model.Property;
import senla.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FavoriteMapper {
    public static Favorite mapRow(ResultSet resultSet) throws SQLException {
        Property property = PropertyMapper.mapRow(resultSet);
        User user = UserMapper.mapRow(resultSet, "fav_user_");
        return Favorite.builder()
                .id(resultSet.getInt("id"))
                .user(user)
                .property(property)
                .build();
    }
}
