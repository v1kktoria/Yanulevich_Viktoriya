package senla.exception;

public class DatabaseConnectionException extends RuntimeException {
    public DatabaseConnectionException(String message) {
        super(message);
    }

    public DatabaseConnectionException(Throwable cause) {
        super(ExceptionEnum.DATABASE_CONNECTION_ERROR.getMessage(), cause);
    }
}
