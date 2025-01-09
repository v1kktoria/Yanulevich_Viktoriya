package senla.util.mapper;

import senla.model.Parameter;
import senla.model.Property;
import senla.model.PropertyParameter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PropertyParameterMapper {
    public static PropertyParameter mapRow(ResultSet resultSet) throws SQLException {
        Property property = PropertyMapper.mapRow(resultSet);
        Parameter parameter = ParameterMapper.mapRow(resultSet);
        return PropertyParameter.builder()
                .property(property)
                .parameter(parameter)
                .value(resultSet.getString("value"))
                .build();
    }
}
