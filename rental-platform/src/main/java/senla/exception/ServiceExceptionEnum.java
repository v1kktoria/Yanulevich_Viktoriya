package senla.exception;

import java.text.MessageFormat;

public enum ServiceExceptionEnum {

    INVALID_DATA("Некорректные данные : {0}", 400),
    USER_ALREADY_EXISTS("Пользователь с именем {0} уже существует", 409),
    CREATION_FAILED("Не удалось создать объект", 500),
    ENTITY_NOT_FOUND("Объект с ID {0} не найден", 404),
    ENTITY_NOT_FOUND_WITH_NAME("Объект с именем {0} не найден", 404),
    PROPERTY_ALREADY_EXIST("Недвижимость с id {0} уже добавлена в избранное", 409),
    REVIEW_ALREADY_EXIST("Отзыв пользователя с id {0} для недвижимости с id {1} уже существует", 409),
    ADDRESS_ALREADY_EXISTS("Адрес для недвижимости с id {0} уже существует",409),
    PROFILE_ALREADY_EXISTS("Профиль для пользователя с id {0} уже существует", 409),
    APPLICATION_ALREADY_EXISTS("Заявка от арендатора с id {0} на недвижимость с id {1} уже существует", 409),
    MINIO_ERROR("Ошибка Minio сервиса: {0}", 500);

    private final String message;
    private final int statusCode;

    ServiceExceptionEnum(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage(Object... args) {
        return MessageFormat.format(message, args);
    }

    public int getStatusCode() {
        return statusCode;
    }
}
