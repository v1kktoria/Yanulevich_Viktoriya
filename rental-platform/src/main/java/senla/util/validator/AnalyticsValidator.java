package senla.util.validator;

import lombok.experimental.UtilityClass;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Analytics;

@UtilityClass
public class AnalyticsValidator {
    public void validate(Analytics analytics) {
        if (analytics.getViews() < 0) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Количество просмотров не может быть меньше нуля");
        } else if (analytics.getApplicationsCount() < 0) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Количество заявок не может быть меньше нуля");
        }
    }
}
