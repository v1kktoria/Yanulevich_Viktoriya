package senla.util.validator;

import lombok.experimental.UtilityClass;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Role;

@UtilityClass
public class RoleValidator {
    public void validate(Role role) {
        if (role.getRoleName().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Имя роли не может быть пустым");
        }
    }
}
