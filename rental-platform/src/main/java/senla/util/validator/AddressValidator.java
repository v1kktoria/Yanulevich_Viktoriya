package senla.util.validator;

import lombok.experimental.UtilityClass;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Address;

@UtilityClass
public class AddressValidator {
    public void validate(Address address) {
        if (address.getCountry().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Название страны не может быть пустым");
        } else if (address.getCity().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Название города не может быть пустым");
        } else if (address.getHouseNumber().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Номер дома не может быть пустым");
        } else if (address.getStreet().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Название улицы не может быть пустым");
        }
    }
}
