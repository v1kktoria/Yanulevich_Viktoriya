package senla.dao.impl;

import senla.dao.AbstractDAO;
import senla.model.mapper.UserMapper;
import senla.model.ENUM.ReportType;
import senla.model.ENUM.Status;
import senla.model.Report;
import senla.model.User;
import senla.util.ConnectionHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ReportDAOImpl extends AbstractDAO<Report, Integer> {

    private static volatile ReportDAOImpl instance;

    public static ReportDAOImpl getInstance(ConnectionHolder connectionHolder) {
        if (instance == null) {
            synchronized (ReportDAOImpl.class) {
                if (instance == null) {
                    instance = new ReportDAOImpl(connectionHolder);
                }
            }
        }
        return instance;
    }

    private static final String SQL_CREATE = "INSERT INTO Reports (user_id, type, content_id, message, status, created_at) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_GET_ALL =
            "SELECT r.*, u.id AS user_id, u.username AS user_username, u.password AS user_password, u.deleted AS user_deleted " +
                    "FROM ActiveReports r " +
                    "JOIN ActiveUsers u ON r.user_id = u.id";

    private static final String SQL_GET_BY_ID =  SQL_GET_ALL + " WHERE r.id = ?";

    private static final String SQL_UPDATE_BY_ID = "UPDATE Reports SET user_id = ?, type = ?, content_id = ?, message = ?, status = ?, created_at = ?, deleted = ? WHERE id = ?";

    private static final String SQL_DELETE_BY_ID = "UPDATE Reports SET deleted = TRUE WHERE id = ?";

    @Override
    protected void prepareStatementForSave(PreparedStatement statement, Report report, boolean isUpdate, Integer id) throws SQLException {
        statement.setInt(1, report.getUser().getId());
        statement.setString(2, report.getType().toString());
        statement.setInt(3, report.getContentId());
        statement.setString(4, report.getMessage());
        statement.setString(5, report.getStatus().toString());
        statement.setTimestamp(6, Timestamp.valueOf(report.getCreatedAt()));
        if (isUpdate) {
            statement.setBoolean(7, report.isDeleted());
            statement.setInt(8, id);
        }
    }

    @Override
    protected Report mapRow(ResultSet resultSet) throws SQLException {
        User user = UserMapper.mapRow(resultSet, "user_");

        ReportType reportType = resultSet.getString("type") != null ? ReportType.valueOf(resultSet.getString("type")) : null;
        Status status = resultSet.getString("status") != null ? Status.valueOf(resultSet.getString("status")) : null;

        return new Report.Builder()
                .setId(resultSet.getInt("id"))
                .setUser(user)
                .setType(reportType)
                .setContentId(resultSet.getInt("content_id"))
                .setMessage(resultSet.getString("message"))
                .setStatus(status)
                .setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                .setDeleted(resultSet.getBoolean("deleted"))
                .build();
    }

    private ReportDAOImpl(ConnectionHolder connectionHolder) {
        super(connectionHolder);
    }

    @Override
    protected String getCreateQuery() {
        return SQL_CREATE;
    }

    @Override
    protected String getByParamQuery(Object param) {
        if (param instanceof Integer) {
            return SQL_GET_BY_ID;
        }
        return null;
    }

    @Override
    protected String getAllQuery() {
        return SQL_GET_ALL;
    }

    @Override
    protected String getUpdateQuery() {
        return SQL_UPDATE_BY_ID;
    }

    @Override
    protected String getDeleteQuery() {
        return SQL_DELETE_BY_ID;
    }
}