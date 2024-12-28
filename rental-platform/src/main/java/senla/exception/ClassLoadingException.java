package senla.exception;

public class ClassLoadingException extends RuntimeException {

    public ClassLoadingException(String message) {
        super(message);
    }

    public static ClassLoadingException forClass(String className) {
        String message = String.format(ExceptionEnum.INVALID_PARAMETER_ERROR.getMessage(), className);
        return new ClassLoadingException(message);
    }
}

