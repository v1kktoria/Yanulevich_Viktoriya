package senla.dao.impl;

import senla.dao.AbstractDAO;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.util.mapper.UserMapper;
import senla.model.User;
import senla.util.ConnectionHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserDAOImpl extends AbstractDAO<User, Integer> {

    private final ConnectionHolder connectionHolder;

    @Autowired
    private UserDAOImpl(ConnectionHolder connectionHolder) {
        super(connectionHolder);
        this.connectionHolder = connectionHolder;
    }

    private static final String SQL_CREATE_USER = "INSERT INTO Users (username, password) VALUES (?, ?)";

    private static final String SQL_GET_ALL_USERS =
            "SELECT u.id AS user_id, u.username AS user_username, u.password AS user_password, u.deleted AS user_deleted FROM ActiveUsers u";
    private static final String SQL_GET_USER_BY_ID = SQL_GET_ALL_USERS + " WHERE u.id = ?";
    private static final String SQL_UPDATE_USER_BY_ID = "UPDATE Users SET username = ?, password = ?, deleted = ? WHERE id = ?";
    private static final String SQL_DELETE_USER_BY_ID = "UPDATE Users SET deleted = TRUE WHERE id = ?";

    private static final String SQL_EXISTS_BY_USERNAME = "SELECT COUNT(*) FROM Users WHERE username = ?";

    @Override
    protected void prepareStatementForSave(PreparedStatement statement, User user, boolean isUpdate, Integer id) throws SQLException {
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        if (isUpdate) {
            statement.setBoolean(3, user.isDeleted());
            statement.setInt(4, id);
        }
    }

    @Override
    protected User mapRow(ResultSet resultSet) throws SQLException {
        return UserMapper.mapRow(resultSet, "user_");
    }

    @Override
    protected String getCreateQuery() {
        return SQL_CREATE_USER;
    }

    @Override
    protected String getByParamQuery(Object param) {
        if (param instanceof  Integer) return SQL_GET_USER_BY_ID;
        return null;
    }

    @Override
    protected String getAllQuery() {
        return SQL_GET_ALL_USERS;
    }

    @Override
    protected String getUpdateQuery() {
        return SQL_UPDATE_USER_BY_ID;
    }

    @Override
    protected String getDeleteQuery() {
        return SQL_DELETE_USER_BY_ID;
    }

    public boolean existsByUsername(String username) {
        boolean exists = false;
        String threadName = Thread.currentThread().getName();

        try {
            Connection connection = connectionHolder.getConnection(threadName);
            try (PreparedStatement statement = connection.prepareStatement(SQL_EXISTS_BY_USERNAME)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        exists = resultSet.getInt(1) > 0;
                    }
                }
            }
            connectionHolder.commit(threadName);
        } catch (SQLException e) {
            connectionHolder.rollback(threadName);
        } finally {
            connectionHolder.close(threadName);
        }
        return exists;
    }
}
