package senla.service.impl;

import senla.dao.impl.AddressDAOImpl;
import senla.dao.impl.PropertyDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Address;
import senla.model.Property;
import senla.service.AddressService;

import java.util.List;

@Component
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDAOImpl addressDAO;

    @Autowired
    private PropertyDAOImpl propertyDAO;

    @Override
    public Address create(Address address) {
        validate(address);
        return addressDAO.create(address);
    }

    @Override
    public Address getById(Integer id) {
        return addressDAO.getByParam(id);
    }

    @Override
    public Address getByPropertyId(Integer id) {
        Property property = propertyDAO.getByParam(id);
        return addressDAO.getByParam(property);
    }

    @Override
    public List<Address> getAll() {
        return addressDAO.getAll();
    }

    @Override
    public void updateById(Integer id, Address address) {
        validate(address);
        addressDAO.updateById(id, address);
    }

    @Override
    public void deleteById(Integer id) {
        addressDAO.deleteById(id);
    }

    private void validate(Address address) {
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
