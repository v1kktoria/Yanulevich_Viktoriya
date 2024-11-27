package senla.model.mapper;

import senla.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static User mapRow(ResultSet resultSet, String prefix) throws SQLException {
        return new User.Builder()
                .setId(resultSet.getInt(prefix + "id"))
                .setUsername(resultSet.getString(prefix +"username"))
                .setPassword(resultSet.getString(prefix +"password"))
                .setDeleted(resultSet.getBoolean(prefix +"deleted"))
                .build();
    }
}
