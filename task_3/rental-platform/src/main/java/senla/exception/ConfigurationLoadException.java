package senla.exception;

public class ConfigurationLoadException extends RuntimeException {
    public ConfigurationLoadException() {
        super(ExceptionEnum.CONFIGURATION_LOAD_ERROR.getMessage());
    }

    public ConfigurationLoadException(Throwable cause) {
        super(ExceptionEnum.CONFIGURATION_LOAD_ERROR.getMessage(), cause);
    }
}


