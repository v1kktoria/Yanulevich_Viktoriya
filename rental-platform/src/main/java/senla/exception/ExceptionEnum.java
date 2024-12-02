package senla.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    CONFIGURATION_LOAD_ERROR("Ошибка при загрузке application.properties"),
    FIELD_ACCESS_ERROR("Не удалось получить доступ к полю 'id' сущности"),
    ENTITY_NOT_FOUND("Сущность не найдена для параметра: %s"),
    TRANSACTION_ERROR("Ошибка транзакции"),
    CONNECTION_CLOSE_ERROR("Ошибка при закрытии соединения"),
    DATABASE_CONNECTION_ERROR("Ошибка при подключении к базе данных"),
    INVALID_PARAMETER_ERROR("Некорректное значение параметра: %s");

    private final String message;
}
