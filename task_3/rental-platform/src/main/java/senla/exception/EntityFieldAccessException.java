package senla.exception;

public class EntityFieldAccessException extends RuntimeException {
    public EntityFieldAccessException(String message) {
        super(message);
    }

    public EntityFieldAccessException(Throwable cause) {
        super(ExceptionEnum.FIELD_ACCESS_ERROR.getMessage(), cause);
    }
}
