package senla.dao;

import senla.exception.EntityFieldAccessException;
import senla.exception.EntityNotFoundException;
import senla.exception.InvalidParameterException;
import senla.util.ConnectionHolder;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<T, ID> implements ParentDAO<T, ID> {

    private final ConnectionHolder connectionHolder;

    public AbstractDAO(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    protected abstract String getCreateQuery();

    protected abstract String getByParamQuery(Object param);

    protected abstract String getAllQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract T mapRow(ResultSet resultSet) throws SQLException;

    protected abstract void prepareStatementForSave(PreparedStatement statement, T entity, boolean isUpdate, ID id) throws SQLException;

    @Override
    public T create(T entity) {
        String threadName = Thread.currentThread().getName();
        try {
            Connection connection = connectionHolder.getConnection(threadName);
            try (PreparedStatement statement = connection.prepareStatement(getCreateQuery())) {
                prepareStatementForSave(statement, entity, false, null);
                statement.executeUpdate();
                setCurrentSeqValue(connection, entity);
                connectionHolder.commit(threadName);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new EntityFieldAccessException(e);
            }
        } catch (SQLException e) {
            connectionHolder.rollback(threadName);
        } finally {
            connectionHolder.close(threadName);
        }
        return entity;
    }

    @Override
    public T getByParam(Object param) {
        T entity = null;
        String threadName = Thread.currentThread().getName();

        try {
            Connection connection = connectionHolder.getConnection(threadName);
            String query = getByParamQuery(param);
            if (query == null) {
                throw InvalidParameterException.forParam(param);
            }
            try (PreparedStatement statement = connection.prepareStatement(query)) {

                if (param != null && !(param instanceof Integer)) {
                    Object paramId = getIdFromEntity(param);
                    if (paramId != null) statement.setObject(1, paramId);
                }
                else statement.setObject(1, param);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        entity = mapRow(resultSet);
                    } else {
                        throw EntityNotFoundException.forParam(param);
                    }
                }
            }
            connectionHolder.commit(threadName);
        } catch (SQLException e) {
            connectionHolder.rollback(threadName);
        } finally {
            connectionHolder.close(threadName);
        }
        return entity;
    }

    @Override
    public List<T> getAll() {
        List<T> entities = new ArrayList<>();
        String threadName = Thread.currentThread().getName();

        try {
            Connection connection = connectionHolder.getConnection(threadName);
            try (PreparedStatement statement = connection.prepareStatement(getAllQuery());
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    entities.add(mapRow(resultSet));
                }
            }
            connectionHolder.commit(threadName);
        } catch (SQLException e) {
            connectionHolder.rollback(threadName);
        } finally {
            connectionHolder.close(threadName);
        }

        return entities;
    }

    @Override
    public void updateById(ID id, T entity) {
        String threadName = Thread.currentThread().getName();
        try {
            Connection connection = connectionHolder.getConnection(threadName);
            try (PreparedStatement statement = connection.prepareStatement(getUpdateQuery())) {
                prepareStatementForSave(statement, entity, true, id);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    throw EntityNotFoundException.forParam(id);
                }
                connectionHolder.commit(threadName);
            }
        } catch (SQLException e) {
            connectionHolder.rollback(threadName);
        } finally {
            connectionHolder.close(threadName);
        }
    }

    @Override
    public void deleteById(ID id) {
        String threadName = Thread.currentThread().getName();
        try {
            Connection connection = connectionHolder.getConnection(threadName);
            try (PreparedStatement statement = connection.prepareStatement(getDeleteQuery())) {
                statement.setObject(1, id);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    throw EntityNotFoundException.forParam(id);
                }
                connectionHolder.commit(threadName);
                System.out.println("Сущность с ID " + id + " успешно удалена");
            }
        } catch (SQLException e) {
            connectionHolder.rollback(threadName);
        } finally {
            connectionHolder.close(threadName);
        }
    }

    private Object getIdFromEntity(Object entity) {
        try {
            Field field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            return field.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new EntityFieldAccessException(e);
        }
    }

    private void setCurrentSeqValue(Connection connection, T entity) throws SQLException, NoSuchFieldException, IllegalAccessException {
        String className = this.getClass().getSimpleName();
        String tableName = className.replace("DAOImpl", "").toLowerCase();
        String seqName;
        if (tableName.equals("property")) tableName = "propertie";
        if (tableName.equals("analytics")) seqName = tableName + "_id_seq";
        else seqName = tableName + "s_id_seq";
        String query = "SELECT currval('" + seqName + "')";
        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            if (resultSet.next()) {
                int id = resultSet.getInt("currval");
                Field idField = entity.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(entity, id);
            }
        }
    }
}
