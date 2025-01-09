package senla.dao.impl;

import senla.dao.AbstractDAO;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.util.mapper.AddressMapper;
import senla.model.Address;
import senla.model.Property;
import senla.util.ConnectionHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AddressDAOImpl extends AbstractDAO<Address, Integer> {

    @Autowired
    private AddressDAOImpl(ConnectionHolder connectionHolder) {
        super(connectionHolder);
    }


    private static final String SQL_CREATE = "INSERT INTO Addresses (property_id, country, city, street, house_number) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_GET_ALL =
            "SELECT a.*, p.id AS property_id, p.*, u.id AS user_id, u.username AS user_username, u.password AS user_password, u.deleted AS user_deleted, " +
                    "p.deleted AS property_deleted, p.created_at AS property_created_at FROM Addresses a " +
                    "JOIN ActiveProperties p ON a.property_id = p.id " +
                    "JOIN ActiveUsers u ON p.owner_id = u.id";

    private static final String SQL_GET_BY_PROPERTY_ID = SQL_GET_ALL + " WHERE p.id = ?";

    private static final String SQL_GET_BY_ID = SQL_GET_ALL + " WHERE a.id = ?";

    private static final String SQL_UPDATE_BY_ID = "UPDATE Addresses SET property_id = ?, country = ?, city = ?, street = ?, house_number = ? WHERE id = ?";

    private static final String SQL_DELETE_BY_ID = "DELETE FROM Addresses WHERE id = ?";

    @Override
    protected void prepareStatementForSave(PreparedStatement statement, Address address, boolean isUpdate, Integer id) throws SQLException {
        statement.setInt(1, address.getProperty().getId());
        statement.setString(2, address.getCountry());
        statement.setString(3, address.getCity());
        statement.setString(4, address.getStreet());
        statement.setString(5, address.getHouseNumber());
        if (isUpdate){
            statement.setInt(6, id);
        }
    }

    @Override
    protected Address mapRow(ResultSet resultSet) throws SQLException {
        return AddressMapper.mapRow(resultSet);
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
