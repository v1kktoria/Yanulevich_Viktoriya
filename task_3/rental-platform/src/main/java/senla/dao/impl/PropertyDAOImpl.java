package senla.dao.impl;

import senla.dao.AbstractDAO;
import senla.model.User;
import senla.util.mapper.PropertyMapper;
import senla.model.Property;
import senla.util.ConnectionHolder;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PropertyDAOImpl extends AbstractDAO<Property, Integer> {
    private static volatile PropertyDAOImpl instance;

    public static PropertyDAOImpl getInstance(ConnectionHolder connectionHolder) {
        if (instance == null) {
            synchronized (PropertyDAOImpl.class) {
                if (instance == null) {
                    instance = new PropertyDAOImpl(connectionHolder);
                }
            }
        }
        return instance;
    }

    private static final String SQL_CREATE = "INSERT INTO Properties (owner_id, type, area, price, rooms, description, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_GET_ALL =
            "SELECT p.*, " +
                    "u.id AS user_id, u.username AS user_username, u.password AS user_password, u.deleted AS user_deleted, " +
                    "p.id AS property_id, p.type AS type, p.area, p.price, p.rooms, p.description, p.created_at AS property_created_at, p.deleted AS property_deleted FROM ActiveProperties p " +
                    "JOIN ActiveUsers u ON p.owner_id = u.id";

    private static final String SQL_GET_BY_ID =  SQL_GET_ALL + " WHERE p.id = ?";

    private static final String SQL_GET_BY_USER_ID =  SQL_GET_ALL + " WHERE u.id = ?";

    private static final String SQL_UPDATE_BY_ID = "UPDATE Properties SET owner_id = ?, type = ?, area = ?, price = ?, rooms = ?, description = ?, created_at = ?, deleted = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "UPDATE Properties SET deleted = TRUE WHERE id = ?";

    private PropertyDAOImpl(ConnectionHolder connectionHolder) {
        super(connectionHolder);
    }

    @Override
    protected void prepareStatementForSave(PreparedStatement statement, Property property, boolean isUpdate, Integer id) throws SQLException {
        statement.setInt(1, property.getOwner().getId());
        statement.setObject(2, property.getType(), java.sql.Types.OTHER);
        statement.setBigDecimal(3, BigDecimal.valueOf(property.getArea()));
        statement.setBigDecimal(4, BigDecimal.valueOf(property.getPrice()));
        statement.setInt(5, property.getRooms());
        statement.setString(6, property.getDescription());
        statement.setTimestamp(7, Timestamp.valueOf(property.getCreatedAt()));

        if (isUpdate) {
            statement.setBoolean(8, property.isDeleted());
            statement.setInt(9, id);
        }
    }

    @Override
    protected Property mapRow(ResultSet resultSet) throws SQLException {
        return PropertyMapper.mapRow(resultSet);
    }

    @Override
    protected String getCreateQuery() {
        return SQL_CREATE;
    }

    @Override
    protected String getByParamQuery(Object param) {
        if (param instanceof User) return SQL_GET_BY_USER_ID;
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