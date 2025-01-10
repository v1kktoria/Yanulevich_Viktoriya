package senla.exception;

import java.text.MessageFormat;

public enum DatabaseExceptionEnum {

    SAVE_FAILED("Не удалось сохранить объект", 500),
    UPDATE_FAILED("Не удалось обновить объект", 500),
    DELETE_FAILED("Не удалось удалить объект с ID {0}", 500),
    DATABASE_ERROR("Ошибка при работе с базой данных: {0}", 500);

    private final String message;
    private final int statusCode;

    DatabaseExceptionEnum(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage(Object... args) {
        return MessageFormat.format(message, args);
    }

    public int getStatusCode() { return statusCode;}
}
