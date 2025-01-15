package senla.exception;

public class DatabaseException extends RuntimeException{
    private final DatabaseExceptionEnum code;

    public DatabaseException(DatabaseExceptionEnum code, Object... args) {
        super(code.getMessage(args));
        this.code = code;
    }

    public int getStatusCode() {
        return code.getStatusCode();
    }

    @Override
    public String toString() {
        return "DatabaseException{" +
                "code=" + code +
                ", message=" + getMessage() +
                '}';
    }
}
