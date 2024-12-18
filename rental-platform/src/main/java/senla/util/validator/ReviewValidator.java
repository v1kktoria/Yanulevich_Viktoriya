package senla.util.validator;

import lombok.experimental.UtilityClass;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Review;

@UtilityClass
public class ReviewValidator {
    public void validate(Review review) {
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Рейтинг должен быть в диапазоне от 1 до 5");
        }
    }
}
