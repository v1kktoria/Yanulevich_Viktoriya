package senla.util.mapper;

import senla.model.Address;
import senla.model.Property;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressMapper {
    public static Address mapRow(ResultSet resultSet) throws SQLException {
        Property property = PropertyMapper.mapRow(resultSet);

        return Address.builder()
                .id(resultSet.getInt("id"))
                .property(property)
                .country(resultSet.getString("country"))
                .city(resultSet.getString("city"))
                .street(resultSet.getString("street"))
                .houseNumber(resultSet.getString("house_number"))
                .build();
    }
}
