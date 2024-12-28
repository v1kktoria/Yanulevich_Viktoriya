package senla.util.mapper;

import senla.model.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper {
    public static Role mapRow(ResultSet resultSet) throws SQLException {
        return Role.builder()
                .id(resultSet.getInt("id"))
                .roleName(resultSet.getString("role_name"))
                .description(resultSet.getString("description"))
                .build();
    }
}
