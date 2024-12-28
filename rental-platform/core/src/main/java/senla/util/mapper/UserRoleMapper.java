package senla.util.mapper;

import senla.model.Role;
import senla.model.User;
import senla.model.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRoleMapper {
    public static UserRole mapRow(ResultSet resultSet) throws SQLException {
        User user = UserMapper.mapRow(resultSet, "");
        Role role = RoleMapper.mapRow(resultSet);
        return UserRole.builder()
                .user(user)
                .role(role)
                .build();
    }
}
