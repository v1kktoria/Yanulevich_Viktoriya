package senla.util;

import senla.dicontainer.annotation.Component;
import senla.dicontainer.annotation.Value;
import senla.exception.ClassLoadingException;
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

@Component
public class ConnectionHolder {

    @Value("db.url")
    private static String URL_KEY;

    @Value("db.password")
    private static String PASSWORD_KEY;

    @Value("db.username")
    private static String USERNAME_KEY;

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
            Connection connection;
            Class.forName("org.postgresql.Driver");
            if (!unusedConnection.isEmpty()) {
                while (true) {
                    connection = unusedConnection.remove(0);
                    if (!connection.isClosed()) break;
                    unusedConnection.remove(connection);
                    if (unusedConnection.isEmpty()) {
                        connection = DriverManager.getConnection(URL_KEY, USERNAME_KEY, PASSWORD_KEY);
                        break;
                    }
                }
            }
            else {
                connection = DriverManager.getConnection(URL_KEY, USERNAME_KEY, PASSWORD_KEY);
            }

            connection.setAutoCommit(false);
            connectionMap.put(threadName, connection);
            return connection;
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e);
        } catch (ClassNotFoundException e) {
            throw ClassLoadingException.forClass("org.postgresql.Driver");
        }
    }

    public void commit(String threadName) {
        try {
            Connection connection = connectionMap.remove(threadName);
            connection.commit();
            unusedConnection.add(connection);
        } catch (SQLException e) {
            throw new TransactionException(e);
        }
    }

    public void rollback(String threadName) {
        try {
            Connection connection = connectionMap.remove(threadName);
            connection.rollback();
            unusedConnection.add(connection);
        } catch (SQLException e) {
            throw new TransactionException(e);
        }
    }

    public void close(String threadName) {
        for (Connection connection : unusedConnection) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new ConnectionCloseException(e);
            }
        }
    }

}
