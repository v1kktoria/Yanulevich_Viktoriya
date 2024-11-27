package senla.exception;

public class ConnectionCloseException extends RuntimeException {
    public ConnectionCloseException(String message) {
        super(message);
    }

    public ConnectionCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
