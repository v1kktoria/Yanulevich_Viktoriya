package senla.util.validator;

import lombok.experimental.UtilityClass;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Image;

@UtilityClass
public class ImageValidator {
    public void validate(Image image) {
        if (image.getFilepath().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Путь файла не может быть пустым");
        }
    }
}
