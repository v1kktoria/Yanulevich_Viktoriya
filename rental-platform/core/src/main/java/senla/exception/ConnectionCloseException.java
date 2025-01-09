package senla.exception;

public class ConnectionCloseException extends RuntimeException {
    public ConnectionCloseException(String message) {
        super(message);
    }

    public ConnectionCloseException(Throwable cause) {
        super(ExceptionEnum.CONNECTION_CLOSE_ERROR.getMessage(), cause);
    }
}
