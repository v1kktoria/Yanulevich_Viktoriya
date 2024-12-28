package senla.util.mapper;

import senla.model.Image;
import senla.model.Property;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageMapper {
    public static Image mapRow(ResultSet resultSet) throws SQLException {
        Property property = PropertyMapper.mapRow(resultSet);

        return Image.builder()
                .id(resultSet.getInt("id"))
                .property(property)
                .filepath(resultSet.getString("filepath"))
                .build();
    }
}
