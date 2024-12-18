package senla.util.validator;

import lombok.experimental.UtilityClass;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Parameter;

@UtilityClass
public class ParameterValidator {
    public void validate(Parameter parameter) {
        if (parameter.getName().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Название параметра не может быть пустым");
        }
    }
}
