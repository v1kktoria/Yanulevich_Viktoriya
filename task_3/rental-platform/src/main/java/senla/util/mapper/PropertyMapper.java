package senla.util.mapper;

import senla.model.constant.PropertyType;
import senla.model.Property;
import senla.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PropertyMapper {
    public static Property mapRow(ResultSet resultSet) throws SQLException {
        User user = UserMapper.mapRow(resultSet, "user_");
        return Property.builder()
                .id(resultSet.getInt("property_id"))
                .owner(user)
                .type(PropertyType.valueOf(resultSet.getString("type")))
                .area(resultSet.getBigDecimal("area").doubleValue())
                .price(resultSet.getBigDecimal("price").doubleValue())
                .rooms(resultSet.getInt("rooms"))
                .description(resultSet.getString("description"))
                .createdAt(resultSet.getTimestamp("property_created_at").toLocalDateTime())
                .deleted(resultSet.getBoolean("property_deleted"))
                .build();
    }
}
