package senla.util.mapper;

import senla.model.Parameter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParameterMapper {
    public static Parameter mapRow(ResultSet resultSet) throws SQLException {
        return Parameter.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .build();
    }
}
