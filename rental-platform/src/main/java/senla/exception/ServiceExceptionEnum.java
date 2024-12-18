package senla.exception;

import java.text.MessageFormat;

public enum ServiceExceptionEnum {

    INVALID_DATA("Некорректные данные : {0}", 400),
    USER_ALREADY_EXISTS("Пользователь с именем {0} уже существует", 409);

    private final String message;
    private final int statusCode;

    ServiceExceptionEnum(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage(Object... args) {
        return MessageFormat.format(message, args);
    }

    public int getStatusCode() { return statusCode;}
}
