package senla.dao.impl;

import senla.dao.PropertyParameterDAO;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Parameter;
import senla.model.Property;
import senla.model.PropertyParameter;
import senla.util.ConnectionHolder;
import senla.util.mapper.PropertyParameterMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PropertyParameterDAOImpl implements PropertyParameterDAO {

    @Autowired
    private ConnectionHolder connectionHolder;

    private PropertyParameterDAOImpl() {}

    private static final String SQL_CREATE =
            "INSERT INTO Properties_Parameters (property_id, parameter_id, value) VALUES (?, ?, ?)";
    private static final String SQL_GET_ALL =
            "SELECT pp.property_id, pp.parameter_id, pp.value, " +
                    "p.id AS property_id, p.type AS type, p.area, p.price, p.rooms, p.description, p.created_at AS property_created_at, p.deleted AS property_deleted, " +
                    "pr.id, pr.name, pr.description, " +
                    "u.id AS user_id, u.username AS user_username, u.password AS user_password, u.deleted AS user_deleted " +
                    "FROM Properties_Parameters pp " +
                    "JOIN ActiveProperties p ON pp.property_id = p.id " +
                    "JOIN Parameters pr ON pp.parameter_id = pr.id " +
                    "JOIN ActiveUsers u ON p.owner_id = u.id";


    private static final String SQL_GET_BY_PROPERTY_AND_PARAMETER = SQL_GET_ALL + " WHERE pp.property_id = ? AND pp.parameter_id = ?";
    private static final String SQL_DELETE_BY_PROPERTY_AND_PARAMETER = "DELETE FROM Properties_Parameters WHERE property_id = ? AND parameter_id = ?";


    @Override
    public void create(PropertyParameter propertyParameter) {
        String threadName = Thread.currentThread().getName();
        try {
            PreparedStatement statement = connectionHolder
                    .getConnection(threadName)
                    .prepareStatement(SQL_CREATE);
            statement.setInt(1, propertyParameter.getProperty().getId());
            statement.setInt(2, propertyParameter.getParameter().getId());
            statement.setString(3, propertyParameter.getValue());
            statement.executeUpdate();
            connectionHolder.commit(threadName);
        } catch (SQLException e) {
            connectionHolder.rollback(threadName);
        } finally {
            connectionHolder.close(threadName);
        }
    }

    @Override
    public PropertyParameter getByPropertyAndParameter(Property property, Parameter parameter) {
        String threadName = Thread.currentThread().getName();
        try {
            PreparedStatement statement = connectionHolder
                    .getConnection(threadName)
                    .prepareStatement(SQL_GET_BY_PROPERTY_AND_PARAMETER);
            statement.setInt(1, property.getId());
            statement.setInt(2, parameter.getId());

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
    public List<PropertyParameter> getAll() {
        String threadName = Thread.currentThread().getName();
        List<PropertyParameter> propertyParameters = new ArrayList<>();
        try {
            Connection connection = connectionHolder.getConnection(threadName);
            try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    propertyParameters.add(mapRow(resultSet));
                }
            }
            connectionHolder.commit(threadName);
        } catch (SQLException e) {
            connectionHolder.rollback(threadName);
        } finally {
            connectionHolder.close(threadName);
        }
        return propertyParameters;
    }

    @Override
    public void deleteByPropertyAndParameter(Property property, Parameter parameter) {
        String threadName = Thread.currentThread().getName();
        try {
            PreparedStatement statement = connectionHolder
                    .getConnection(threadName)
                    .prepareStatement(SQL_DELETE_BY_PROPERTY_AND_PARAMETER);
            statement.setInt(1, property.getId());
            statement.setInt(2, parameter.getId());
            statement.executeUpdate();
            connectionHolder.commit(threadName);
        } catch (SQLException e) {
            connectionHolder.rollback(threadName);
        } finally {
            connectionHolder.close(threadName);
        }
    }

    private PropertyParameter mapRow(ResultSet resultSet) throws SQLException {
        return PropertyParameterMapper.mapRow(resultSet);
    }
}
