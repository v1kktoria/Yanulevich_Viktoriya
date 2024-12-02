package senla.util.mapper;

import senla.model.Application;
import senla.model.Property;
import senla.model.User;
import senla.model.constant.Status;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationMapper {
    public static Application mapRow(ResultSet resultSet) throws SQLException {
        Property property = PropertyMapper.mapRow(resultSet);

        User tenant = UserMapper.mapRow(resultSet, "tenant_");

        return Application.builder()
                .id(resultSet.getInt("id"))
                .property(property)
                .tenant(tenant)
                .message(resultSet.getString("message"))
                .status(Status.valueOf(resultSet.getString("status")))
                .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                .deleted(resultSet.getBoolean("deleted"))
                .build();
    }
}
