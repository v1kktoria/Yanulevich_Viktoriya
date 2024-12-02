package senla.util.mapper;

import senla.model.Profile;
import senla.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileMapper {
    public static Profile mapRow(ResultSet resultSet) throws SQLException {
        User user = UserMapper.mapRow(resultSet, "user_");

        return Profile.builder()
                .id(resultSet.getInt("id"))
                .firstname(resultSet.getString("first_name"))
                .lastname(resultSet.getString("last_name"))
                .email(resultSet.getString("email"))
                .phone(resultSet.getString("phone"))
                .registrationDate(resultSet.getTimestamp("registration_date").toLocalDateTime())
                .user(user)
                .build();
    }
}
