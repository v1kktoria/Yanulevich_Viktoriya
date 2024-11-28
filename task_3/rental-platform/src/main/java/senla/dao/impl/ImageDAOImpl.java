package senla.dao.impl;

import senla.dao.AbstractDAO;
import senla.util.mapper.ImageMapper;
import senla.model.Image;
import senla.util.ConnectionHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageDAOImpl extends AbstractDAO<Image, Integer> {

    private static volatile ImageDAOImpl instance;

    public static ImageDAOImpl getInstance(ConnectionHolder connectionHolder) {
        if (instance == null) {
            synchronized (ImageDAOImpl.class) {
                if (instance == null) {
                    instance = new ImageDAOImpl(connectionHolder);
                }
            }
        }
        return instance;
    }

    private static final String SQL_CREATE = "INSERT INTO Images (property_id, filepath) VALUES (?, ?)";

    private static final String SQL_GET_ALL = "SELECT i.*, p.id AS property_id, p.*, p.deleted AS property_deleted, p.created_at AS property_created_at, " +
            "u.id AS user_id, u.username AS user_username, u.password AS user_password, u.deleted AS user_deleted FROM Images i " +
            "JOIN ActiveProperties p ON i.property_id = p.id " +
            "JOIN ActiveUsers u ON p.owner_id = u.id";

    private static final String SQL_GET_BY_ID = SQL_GET_ALL + " WHERE i.id = ?";

    private static final String SQL_UPDATE_BY_ID = "UPDATE Images SET property_id = ?, filepath = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM Images WHERE id = ?";

    @Override
    protected void prepareStatementForSave(PreparedStatement statement, Image image, boolean isUpdate, Integer id) throws SQLException {
        statement.setInt(1, image.getProperty().getId());
        statement.setString(2, image.getFilepath());
        if (isUpdate) {
            statement.setInt(3, id);
        }
    }

    @Override
    protected Image mapRow(ResultSet resultSet) throws SQLException {
        return ImageMapper.mapRow(resultSet);
    }

    private ImageDAOImpl(ConnectionHolder connectionHolder) {
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
