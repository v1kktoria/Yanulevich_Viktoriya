package senla.dao.impl;

import senla.dao.AbstractDAO;
import senla.model.Parameter;
import senla.util.ConnectionHolder;
import senla.util.mapper.ParameterMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParameterDAOImpl extends AbstractDAO<Parameter, Integer> {

    private static volatile ParameterDAOImpl instance;

    public static ParameterDAOImpl getInstance(ConnectionHolder connectionHolder) {
        if (instance == null) {
            synchronized (ParameterDAOImpl.class) {
                if (instance == null) {
                    instance = new ParameterDAOImpl(connectionHolder);
                }
            }
        }
        return instance;
    }

    private static final String SQL_CREATE = "INSERT INTO Parameters (name, description) VALUES (?, ?)";
    private static final String SQL_GET_BY_ID = "SELECT * FROM Parameters WHERE id = ?";
    private static final String SQL_GET_ALL = "SELECT * FROM Parameters";
    private static final String SQL_UPDATE_BY_ID = "UPDATE Parameters SET name = ?, description = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM Parameters WHERE id = ?";

    @Override
    protected void prepareStatementForSave(PreparedStatement statement, Parameter parameter, boolean isUpdate, Integer id) throws SQLException {
        statement.setString(1, parameter.getName());
        statement.setString(2, parameter.getDescription());
        if (isUpdate) {
            statement.setInt(3, id);
        }
    }

    @Override
    protected Parameter mapRow(ResultSet resultSet) throws SQLException {
        return ParameterMapper.mapRow(resultSet);
    }

    private ParameterDAOImpl(ConnectionHolder connectionHolder) {
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
