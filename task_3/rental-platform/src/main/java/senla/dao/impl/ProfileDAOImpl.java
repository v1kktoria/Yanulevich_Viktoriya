package senla.dao.impl;

import senla.dao.AbstractDAO;
import senla.util.mapper.ProfileMapper;
import senla.model.Profile;
import senla.util.ConnectionHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ProfileDAOImpl extends AbstractDAO<Profile, Integer> {

    private static volatile ProfileDAOImpl instance;

    public static ProfileDAOImpl getInstance(ConnectionHolder connectionHolder) {
        if (instance == null) {
            synchronized (ProfileDAOImpl.class) {
                if (instance == null) {
                    instance = new ProfileDAOImpl(connectionHolder);
                }
            }
        }
        return instance;
    }

    private static final String SQL_CREATE = "INSERT INTO Profiles (first_name, last_name, email, phone, registration_date) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_GET_ALL =
            "SELECT p.*, u.id AS user_id, u.username AS user_username, u.password AS user_password, u.deleted AS user_deleted " +
                    "FROM Profiles p " +
                    "JOIN ActiveUsers u ON p.id = u.id";

    private static final String SQL_GET_BY_ID = SQL_GET_ALL + " WHERE p.id = ?";

    private static final String SQL_UPDATE_BY_ID = "UPDATE Profiles SET first_name = ?, last_name = ?, email = ?, phone = ?, registration_date = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM Profiles WHERE id = ?";

    @Override
    protected void prepareStatementForSave(PreparedStatement statement, Profile profile, boolean isUpdate, Integer id) throws SQLException {
        statement.setString(1, profile.getFirstname());
        statement.setString(2, profile.getLastname());
        statement.setString(3, profile.getEmail());
        statement.setString(4, profile.getPhone());
        statement.setTimestamp(5, Timestamp.valueOf(profile.getRegistrationDate()));

        if (isUpdate) {
            statement.setInt(6, id);
        }
    }

    @Override
    protected Profile mapRow(ResultSet resultSet) throws SQLException {
        return ProfileMapper.mapRow(resultSet);
    }

    private ProfileDAOImpl(ConnectionHolder connectionHolder) {
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
