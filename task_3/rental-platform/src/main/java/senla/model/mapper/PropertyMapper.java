package senla.model.mapper;

import senla.model.ENUM.PropertyType;
import senla.model.Property;
import senla.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PropertyMapper {
    public static Property mapRow(ResultSet resultSet) throws SQLException {
        User user = UserMapper.mapRow(resultSet, "user_");
        return new Property.Builder()
                .setId(resultSet.getInt("property_id"))
                .setOwner(user)
                .setType(PropertyType.valueOf(resultSet.getString("type")))
                .setArea(resultSet.getBigDecimal("area").doubleValue())
                .setPrice(resultSet.getBigDecimal("price").doubleValue())
                .setRooms(resultSet.getInt("rooms"))
                .setDescription(resultSet.getString("description"))
                .setCreatedAt(resultSet.getTimestamp("property_created_at").toLocalDateTime())
                .setDeleted(resultSet.getBoolean("property_deleted"))
                .build();
    }
}
