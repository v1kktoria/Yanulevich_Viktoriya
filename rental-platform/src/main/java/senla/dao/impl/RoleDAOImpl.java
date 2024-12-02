package senla.dao.impl;

import senla.dao.AbstractDAO;
import senla.model.Role;
import senla.util.ConnectionHolder;
import senla.util.mapper.RoleMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAOImpl extends AbstractDAO<Role, Integer> {

    private static volatile RoleDAOImpl instance;

    public static RoleDAOImpl getInstance(ConnectionHolder connectionHolder) {
        if (instance == null) {
            synchronized (RoleDAOImpl.class) {
                if (instance == null) {
                    instance = new RoleDAOImpl(connectionHolder);
                }
            }
        }
        return instance;
    }

    private static final String SQL_CREATE = "INSERT INTO Roles (role_name, description) VALUES (?, ?)";
    private static final String SQL_GET_BY_ID = "SELECT * FROM Roles WHERE id = ?";
    private static final String SQL_GET_ALL = "SELECT * FROM Roles";
    private static final String SQL_UPDATE_BY_ID = "UPDATE Roles SET role_name = ?, description = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM Roles WHERE id = ?";

    @Override
    protected void prepareStatementForSave(PreparedStatement statement, Role role, boolean isUpdate, Integer id) throws SQLException {
        statement.setString(1, role.getRoleName());
        statement.setString(2, role.getDescription());

        if (isUpdate) {
            statement.setInt(3, id);
        }
    }

    @Override
    protected Role mapRow(ResultSet resultSet) throws SQLException {
        return RoleMapper.mapRow(resultSet);
    }

    private RoleDAOImpl(ConnectionHolder connectionHolder) {
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
