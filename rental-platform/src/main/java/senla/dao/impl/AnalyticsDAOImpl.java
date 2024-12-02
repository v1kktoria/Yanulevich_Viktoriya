package senla.dao.impl;

import senla.dao.AbstractDAO;
import senla.util.mapper.AnalyticsMapper;
import senla.model.Analytics;
import senla.model.Property;
import senla.util.ConnectionHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AnalyticsDAOImpl extends AbstractDAO<Analytics, Integer> {

    private static volatile AnalyticsDAOImpl instance;

    public static AnalyticsDAOImpl getInstance(ConnectionHolder connectionHolder) {
        if (instance == null) {
            synchronized (AnalyticsDAOImpl.class) {
                if (instance == null) {
                    instance = new AnalyticsDAOImpl(connectionHolder);
                }
            }
        }
        return instance;
    }

    private static final String SQL_CREATE = "INSERT INTO Analytics (property_id, views, applications_count, created_at) VALUES (?, ?, ?, ?)";
    private static final String SQL_GET_ALL =
            "SELECT a.*, p.id AS property_id, p.*, u.id AS user_id, u.username, u.password, u.deleted AS user_deleted, " +
                    "p.deleted AS property_deleted, p.created_at AS property_created_at FROM Analytics a " +
                    "JOIN ActiveProperties p ON a.property_id = p.id " +
                    "JOIN ActiveUsers u ON p.owner_id = u.id";
    private static final String SQL_GET_BY_ID = SQL_GET_ALL + " WHERE a.id = ?";

    private static final String SQL_GET_BY_PROPERTY_ID = SQL_GET_ALL + " WHERE a.property_id = ?";

    private static final String SQL_UPDATE_BY_ID = "UPDATE Analytics SET property_id = ?, views = ?, applications_count = ?, created_at = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM Analytics WHERE id = ?";

    @Override
    protected void prepareStatementForSave(PreparedStatement statement, Analytics analytics, boolean isUpdate, Integer id) throws SQLException {
        statement.setInt(1, analytics.getProperty().getId());
        statement.setInt(2, analytics.getViews());
        statement.setInt(3, analytics.getApplicationsCount());
        statement.setTimestamp(4, Timestamp.valueOf(analytics.getCreatedAt()));
        if (isUpdate) {
            statement.setInt(5, id);
        }
    }

    @Override
    protected Analytics mapRow(ResultSet resultSet) throws SQLException {
        return AnalyticsMapper.mapRow(resultSet);
    }

    public AnalyticsDAOImpl(ConnectionHolder connectionHolder) {
        super(connectionHolder);
    }

    @Override
    protected String getCreateQuery() {
        return SQL_CREATE;
    }

    @Override
    protected String getByParamQuery(Object param) {
        if (param instanceof Property) return SQL_GET_BY_PROPERTY_ID;
        else if (param instanceof Integer) return SQL_GET_BY_ID;
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
