package senla.util.mapper;

import senla.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static User mapRow(ResultSet resultSet, String prefix) throws SQLException {
        return User.builder()
                .id(resultSet.getInt(prefix + "id"))
                .username(resultSet.getString(prefix +"username"))
                .password(resultSet.getString(prefix +"password"))
                .deleted(resultSet.getBoolean(prefix +"deleted"))
                .build();
    }
}
