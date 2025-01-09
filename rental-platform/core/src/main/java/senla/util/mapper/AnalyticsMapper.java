package senla.util.mapper;

import senla.model.Analytics;
import senla.model.Property;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnalyticsMapper {
    public static Analytics mapRow(ResultSet resultSet) throws SQLException {
        Property property = PropertyMapper.mapRow(resultSet);

        return   Analytics.builder()
                .id(resultSet.getInt("id"))
                .property(property)
                .views(resultSet.getInt("views"))
                .applicationsCount(resultSet.getInt("applications_count"))
                .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                .build();
    }
}
