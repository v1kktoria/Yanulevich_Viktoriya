package senla.dao.impl;

import senla.dao.AbstractDAO;
import senla.model.mapper.PropertyMapper;
import senla.model.mapper.UserMapper;
import senla.model.Application;
import senla.model.ENUM.Status;
import senla.model.Property;
import senla.model.User;
import senla.util.ConnectionHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ApplicationDAOImpl extends AbstractDAO<Application, Integer> {

    private static volatile ApplicationDAOImpl instance;

    public static ApplicationDAOImpl getInstance(ConnectionHolder connectionHolder) {
        if (instance == null) {
            synchronized (ApplicationDAOImpl.class) {
                if (instance == null) {
                    instance = new ApplicationDAOImpl(connectionHolder);
                }
            }
        }
        return instance;
    }

    private static final String SQL_CREATE = "INSERT INTO Applications (property_id, tenant_id, message, status, created_at) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_GET_ALL =
            "SELECT a.id AS application_id, a.property_id, a.tenant_id, a.message, a.status, a.created_at, a.deleted, " +
                    "p.id AS property_id, p.*, p.created_at AS property_created_at, p.deleted AS property_deleted, " +
                    "u.id AS tenant_id, u.username AS tenant_username, u.password AS tenant_password, u.deleted AS tenant_deleted, " +
                    "pu.id AS user_id, pu.username AS user_username, pu.password AS user_password, pu.deleted AS user_deleted FROM ActiveApplications a " +
                    "JOIN ActiveProperties p ON a.property_id = p.id " +
                    "JOIN ActiveUsers u ON a.tenant_id = u.id " +
                    "JOIN ActiveUsers pu ON p.owner_id = pu.id";
    private static final String SQL_GET_BY_ID = SQL_GET_ALL + " WHERE a.id = ?";

    private static final String SQL_GET_BY_PROPERTY_ID = SQL_GET_ALL + " WHERE a.property_id = ?";

    private static final String SQL_UPDATE_BY_ID = "UPDATE Applications SET property_id = ?, tenant_id = ?, message = ?, status = ?, created_at = ?, deleted = ? WHERE id = ?";

    private static final String SQL_DELETE_BY_ID = "UPDATE Applications SET deleted = TRUE WHERE id = ?";

    @Override
    protected void prepareStatementForSave(PreparedStatement statement, Application application, boolean isUpdate, Integer id) throws SQLException {
        statement.setInt(1, application.getProperty().getId());
        statement.setInt(2, application.getTenant().getId());
        statement.setString(3, application.getMessage());
        statement.setString(4, application.getStatus().name());
        statement.setTimestamp(5, Timestamp.valueOf(application.getCreatedAt()));
        if (isUpdate) {
            statement.setBoolean(6, application.isDeleted());
            statement.setInt(7, id);
        }
    }

    @Override
    protected Application mapRow(ResultSet resultSet) throws SQLException {
        Property property = PropertyMapper.mapRow(resultSet);

        User tenant = UserMapper.mapRow(resultSet, "tenant_");

        return new Application.Builder()
                .setId(resultSet.getInt("id"))
                .setProperty(property)
                .setTenant(tenant)
                .setMessage(resultSet.getString("message"))
                .setStatus(Status.valueOf(resultSet.getString("status")))
                .setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                .setDeleted(resultSet.getBoolean("deleted"))
                .build();
    }

    private ApplicationDAOImpl(ConnectionHolder connectionHolder) {
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
        } else if (param instanceof Property) {
            return SQL_GET_BY_PROPERTY_ID;
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