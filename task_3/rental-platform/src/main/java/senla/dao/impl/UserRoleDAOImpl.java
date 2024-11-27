package senla.dao.impl;

import senla.dao.UserRoleDAO;
import senla.model.mapper.UserMapper;
import senla.model.Role;
import senla.model.User;
import senla.model.UserRole;
import senla.util.ConnectionHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDAOImpl implements UserRoleDAO {

    private static volatile UserRoleDAOImpl instance;
    private final ConnectionHolder connectionHolder;

    public static UserRoleDAOImpl getInstance(ConnectionHolder connectionHolder) {
        if (instance == null) {
            synchronized (UserRoleDAOImpl.class) {
                if (instance == null) {
                    instance = new UserRoleDAOImpl(connectionHolder);
                }
            }
        }
        return instance;
    }

    private static final String SQL_CREATE = "INSERT INTO Users_Roles (user_id, role_id) VALUES (?, ?)";
    private static final String SQL_GET_ALL =
            "SELECT ur.user_id, ur.role_id, u.username, u.password, u.id, u.deleted, r.id AS role_id, r.role_name, r.description FROM Users_Roles ur " +
                    "JOIN ActiveUsers u ON ur.user_id = u.id " +
                    "JOIN Roles r ON ur.role_id = r.id";

    private static final String SQL_GET_BY_USER_AND_ROLE = SQL_GET_ALL + " WHERE ur.user_id = ? AND ur.role_id = ?";
    private static final String SQL_DELETE_BY_USER_AND_ROLE = "DELETE FROM Users_Roles WHERE user_id = ? AND role_id = ?";

    private UserRoleDAOImpl(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }


    @Override
    public void create(UserRole userRole) {
        String threadName = Thread.currentThread().getName();
        try {
            PreparedStatement statement = connectionHolder
                    .getConnection(threadName)
                    .prepareStatement(SQL_CREATE);
            statement.setInt(1, userRole.getUser().getId());
            statement.setInt(2, userRole.getRole().getId());
            statement.executeUpdate();
            connectionHolder.commit(threadName);
        } catch (SQLException e) {
            connectionHolder.rollback(threadName);
        } finally {
            connectionHolder.close(threadName);
        }
    }

    @Override
    public UserRole getByUserAndRole(User user, Role role) {
        String threadName = Thread.currentThread().getName();
        try {
            PreparedStatement statement = connectionHolder
                    .getConnection(threadName)
                    .prepareStatement(SQL_GET_BY_USER_AND_ROLE);
            statement.setInt(1, user.getId());
            statement.setInt(2, role.getId());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapRow(resultSet);
            }
            connectionHolder.commit(threadName);
        } catch (SQLException e) {
            connectionHolder.rollback(threadName);
        } finally {
            connectionHolder.close(threadName);
        }
        return null;
    }

    @Override
    public List<UserRole> getAll() {
        String threadName = Thread.currentThread().getName();
        List<UserRole> userRoles = new ArrayList<>();
        try {
            Connection connection = connectionHolder.getConnection(threadName);
            try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    userRoles.add(mapRow(resultSet));
                }
            }
            connectionHolder.commit(threadName);
        } catch (SQLException e) {
            connectionHolder.rollback(threadName);
        } finally {
            connectionHolder.close(threadName);
        }
        return userRoles;
    }

    @Override
    public void deleteByUserAndRole(User user, Role role) {
        String threadName = Thread.currentThread().getName();
        try {
            PreparedStatement statement = connectionHolder
                    .getConnection(threadName)
                    .prepareStatement(SQL_DELETE_BY_USER_AND_ROLE);
            statement.setInt(1, user.getId());
            statement.setInt(2, role.getId());
            statement.executeUpdate();
            connectionHolder.commit(threadName);
        } catch (SQLException e) {
            connectionHolder.rollback(threadName);
        } finally {
            connectionHolder.close(threadName);
        }
    }

    private UserRole mapRow(ResultSet resultSet) throws SQLException {
        User user = UserMapper.mapRow(resultSet, "");

        Role role = new Role.Builder()
                .setId(resultSet.getInt("role_id"))
                .setRoleName(resultSet.getString("role_name"))
                .setDescription(resultSet.getString("description"))
                .build();

        return new UserRole.Builder()
                .setUser(user)
                .setRole(role)
                .build();
    }
}
