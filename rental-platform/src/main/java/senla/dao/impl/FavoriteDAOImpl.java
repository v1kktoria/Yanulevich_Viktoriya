package senla.dao.impl;

import senla.dao.AbstractDAO;
import senla.util.mapper.FavoriteMapper;
import senla.model.Favorite;
import senla.model.User;
import senla.util.ConnectionHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FavoriteDAOImpl extends AbstractDAO<Favorite, Integer> {

    private static volatile FavoriteDAOImpl instance;

    public static FavoriteDAOImpl getInstance(ConnectionHolder connectionHolder) {
        if (instance == null) {
            synchronized (FavoriteDAOImpl.class) {
                if (instance == null) {
                    instance = new FavoriteDAOImpl(connectionHolder);
                }
            }
        }
        return instance;
    }

    private static final String SQL_CREATE = "INSERT INTO Favorites (user_id, property_id) VALUES (?, ?)";

    private static final String SQL_GET_ALL =
            "SELECT f.id AS favorite_id, f.user_id AS fav_user_user_id, f.property_id AS favorite_property_id, " +
                    "u.id AS fav_user_id, u.username AS fav_user_username, u.password AS fav_user_password, u.deleted AS fav_user_deleted, " +
                    "p.id AS property_id, p.type AS type, p.area, p.price, p.rooms, p.description, p.created_at AS property_created_at, p.deleted AS property_deleted, " +
                    "pu.id AS user_id, pu.username AS user_username, pu.password AS user_password, pu.deleted AS user_deleted " +
                    "FROM Favorites f JOIN ActiveUsers u ON f.user_id = u.id " +
                    "JOIN ActiveProperties p ON f.property_id = p.id " +
                    "JOIN ActiveUsers pu ON p.owner_id = pu.id";
    private static final String SQL_GET_BY_ID = SQL_GET_ALL + " WHERE f.id = ?";

    private static final String SQL_GET_BY_USER_ID = SQL_GET_ALL + " WHERE f.user_id = ?";

    private static final String SQL_UPDATE_BY_ID = "UPDATE Favorites SET user_id = ?, property_id = ? WHERE id = ?";

    private static final String SQL_DELETE_BY_ID = "DELETE FROM Favorites WHERE id = ?";

    @Override
    protected void prepareStatementForSave(PreparedStatement statement, Favorite favorite, boolean isUpdate, Integer id) throws SQLException {
        statement.setInt(1, favorite.getUser().getId());
        statement.setInt(2, favorite.getProperty().getId());
        if (isUpdate) {
            statement.setInt(3, id);
        }
    }

    @Override
    protected Favorite mapRow(ResultSet resultSet) throws SQLException {
        return FavoriteMapper.mapRow(resultSet);
    }

    private FavoriteDAOImpl(ConnectionHolder connectionHolder) {
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
        } else if (param instanceof User) {
            return SQL_GET_BY_USER_ID;
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