package senla.util.mapper;

import senla.model.Report;
import senla.model.User;
import senla.model.constant.ReportType;
import senla.model.constant.Status;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportMapper {
    public static Report mapRow(ResultSet resultSet) throws SQLException {
        User user = UserMapper.mapRow(resultSet, "user_");

        return Report.builder()
                .id(resultSet.getInt("id"))
                .user(user)
                .type(ReportType.valueOf(resultSet.getString("type")))
                .contentId(resultSet.getInt("content_id"))
                .message(resultSet.getString("message"))
                .status(Status.valueOf(resultSet.getString("status")))
                .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                .deleted(resultSet.getBoolean("deleted"))
                .build();
    }
}
