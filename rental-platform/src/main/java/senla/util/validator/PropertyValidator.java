package senla.util.validator;

import lombok.experimental.UtilityClass;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Property;

@UtilityClass
public class PropertyValidator {
    public void validate(Property property) {
        if (property.getArea() < 0) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Площадь не может быть меньше нуля");
        } else if (property.getRooms() < 0) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Количество комнат не может быть меньше нуля");
        } else if (property.getPrice() < 0) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Цена не может быть меньше нуля");
        }
    }
}
