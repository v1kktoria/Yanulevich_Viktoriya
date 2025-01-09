package senla.dicontainer.exception;

import java.text.MessageFormat;

public enum DIExceptionEnum {
    NO_IMPLEMENTATION_FOUND("Не найдено ни одной реализации для интерфейса {0}"),
    MULTIPLE_IMPLEMENTATIONS_FOUND("Найдено несколько реализаций для интерфейса {0}. Ожидалась одна реализация интерфейса или использование @Qualifier"),
    FIELD_INJECTION_FAILED("Ошибка при внедрении зависимости в поле"),
    METHOD_INJECTION_FAILED("Ошибка при внедрении зависимости через сеттер"),
    CONSTRUCTOR_INJECTION_FAILED("Ошибка при внедрении зависимости через конструктор"),
    DEFAULT_CONSTRUCTOR_NOT_FOUND("Дефолтный конструктор не найден для класса {0}"),
    CLASS_NOT_FOUND("Не удалось найти класс {0}"),
    CONFIGURATION_LOAD_ERROR("Ошибка при загрузке application.properties"),
    FILE_READ_ERROR("Не удалось прочитать ресурс по пути: {0}");

    private final String message;

    DIExceptionEnum(String message) {
        this.message = message;
    }

    public String getMessage(Object... args) {
        return MessageFormat.format(message, args);
    }
}
