package senla.util.validator;

import lombok.experimental.UtilityClass;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.User;

@UtilityClass
public class UserValidator {
    public void validate(User user) {
        if (user.getUsername().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Имя пользователя не может быть пустым");
        }

        if (user.getPassword().isEmpty() || user.getPassword().length() < 8) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Пароль должен быть не меньше 8 символов");
        }
    }
}
