package senla.util;

import senla.exception.ConnectionCloseException;
import senla.exception.DatabaseConnectionException;
import senla.exception.TransactionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionHolder {

    private static final String URL_KEY = "db.url";
    private static final String PASSWORD_KEY = "db.password";
    private static final String USERNAME_KEY = "db.username";

    private final Map<String, Connection> connectionMap;
    private final List<Connection> unusedConnection;

    public ConnectionHolder() {
        connectionMap = new HashMap<>();
        unusedConnection = new ArrayList<>();
    }

    public synchronized Connection getConnection(String threadName){
        if (connectionMap.containsKey(threadName)) {
            return connectionMap.get(threadName);
        }

        try {
            String url = PropertiesUtil.get(URL_KEY);
            String username = PropertiesUtil.get(USERNAME_KEY);
            String password = PropertiesUtil.get(PASSWORD_KEY);

            Connection connection;
            if (!unusedConnection.isEmpty()) {
                while (true) {
                    connection = unusedConnection.remove(0);
                    if (!connection.isClosed()) break;
                    unusedConnection.remove(connection);
                    if (unusedConnection.isEmpty()) {
                        connection = DriverManager.getConnection(url, username, password);
                        break;
                    }
                }
            }
            else {
                connection = DriverManager.getConnection(url, username, password);
            }

            connection.setAutoCommit(false);
            connectionMap.put(threadName, connection);
            return connection;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Ошибка при получении соединения", e);
        }
    }

    public void commit(String threadName) {
        try {
            Connection connection = connectionMap.remove(threadName);
            connection.commit();
            unusedConnection.add(connection);
        } catch (SQLException e) {
            throw new TransactionException("Ошибка при коммите транзакции", e);
        }
    }

    public void rollback(String threadName) {
        try {
            Connection connection = connectionMap.remove(threadName);
            connection.rollback();
            unusedConnection.add(connection);
        } catch (SQLException e) {
            throw new TransactionException("Ошибка при коммите транзакции", e);
        }
    }

    public void close(String threadName) {
        for (Connection connection : unusedConnection) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new ConnectionCloseException("Ошибка при закрытии соединения", e);
            }
        }
    }

}
