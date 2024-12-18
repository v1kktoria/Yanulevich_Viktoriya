package senla.util.validator;

import lombok.experimental.UtilityClass;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Profile;

@UtilityClass
public class ProfileValidator {
    public void validate(Profile profile) {
        if (profile.getFirstname() == null || profile.getFirstname().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Имя не может быть пустым");
        }
        if (profile.getLastname() == null || profile.getLastname().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Фамилия не может быть пустой");
        }
        if (!profile.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Некорректный email");
        }
        if (profile.getPhone().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Телефон не может быть пустым");
        }
    }
}
