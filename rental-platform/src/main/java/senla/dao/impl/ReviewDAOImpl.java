package senla.dao.impl;

import senla.dao.AbstractDAO;
import senla.util.mapper.ReviewMapper;
import senla.model.Review;
import senla.util.ConnectionHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewDAOImpl extends AbstractDAO<Review, Integer> {

    private static volatile ReviewDAOImpl instance;

    public static ReviewDAOImpl getInstance(ConnectionHolder connectionHolder) {
        if (instance == null) {
            synchronized (ReviewDAOImpl.class) {
                if (instance == null) {
                    instance = new ReviewDAOImpl(connectionHolder);
                }
            }
        }
        return instance;
    }

    private static final String SQL_CREATE = "INSERT INTO Reviews (property_id, user_id, rating, comment, created_at) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_GET_ALL =
            "SELECT r.id, r.property_id, r.user_id AS review_user_id, r.rating, r.comment, r.created_at, r.deleted, " +
                    "p.id AS property_id, p.type, p.area, p.price, p.rooms, p.description, p.created_at AS property_created_at, p.deleted AS property_deleted, " +
                    "pu.id AS user_id, pu.username AS user_username, pu.password AS user_password, pu.deleted AS user_deleted, " +
                    "ru.id AS review_user_id, ru.username AS review_user_username, ru.password AS review_user_password, ru.deleted AS review_user_deleted FROM ActiveReviews r " +
                    "JOIN ActiveProperties p ON r.property_id = p.id " +
                    "JOIN ActiveUsers pu ON p.owner_id = pu.id " +
                    "JOIN ActiveUsers ru ON r.user_id = ru.id";

    private static final String SQL_GET_BY_ID = SQL_GET_ALL + " WHERE r.id = ?";

    private static final String SQL_UPDATE_BY_ID = "UPDATE Reviews SET property_id = ?, user_id = ?, rating = ?, comment = ?, created_at = ?, deleted = ? WHERE id = ?";

    private static final String SQL_DELETE_BY_ID = "UPDATE Reviews SET deleted = TRUE WHERE id = ?";

    @Override
    protected void prepareStatementForSave(PreparedStatement statement, Review review, boolean isUpdate, Integer id) throws SQLException {
        statement.setInt(1, review.getProperty().getId());
        statement.setInt(2, review.getUser().getId());
        statement.setInt(3, review.getRating());
        statement.setString(4, review.getComment());
        statement.setTimestamp(5, review.getCreatedAt());
        if (isUpdate) {
            statement.setBoolean(6, review.isDeleted());
            statement.setInt(7, id);
        }
    }

    @Override
    protected Review mapRow(ResultSet resultSet) throws SQLException {
        return ReviewMapper.mapRow(resultSet);
    }

    private ReviewDAOImpl(ConnectionHolder connectionHolder) {
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
