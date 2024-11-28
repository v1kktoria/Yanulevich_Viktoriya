package senla.exception;

public class InvalidParameterException extends RuntimeException{
        public InvalidParameterException(String message) {
            super(message);
        }

        public static InvalidParameterException forParam(Object param) {
            String message = String.format(ExceptionEnum.INVALID_PARAMETER_ERROR.getMessage(), param);
            return new InvalidParameterException(message);
        }
    }

