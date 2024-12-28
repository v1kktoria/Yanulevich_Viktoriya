package senla.exception;

public class ServiceException extends RuntimeException {
    private final ServiceExceptionEnum code;

    public ServiceException(ServiceExceptionEnum code, Object... args) {
        super(code.getMessage(args));
        this.code = code;
    }

    public int getStatusCode() {
        return code.getStatusCode();
    }

    @Override
    public String toString() {
        return "ServiceException{" +
                "code=" + code +
                ", message=" + getMessage() +
                '}';
    }
}
