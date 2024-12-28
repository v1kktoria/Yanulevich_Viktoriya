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
import senla.util.validator.AddressValidator;

import java.util.List;
import java.util.Optional;

@Component
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDAOImpl addressDAO;

    @Autowired
    private PropertyDAOImpl propertyDAO;

    @Override
    public Optional<Address> create(Address address) {
        AddressValidator.validate(address);
        return Optional.ofNullable(addressDAO.create(address));
    }

    @Override
    public Optional<Address> getById(Integer id) {
        return Optional.ofNullable(addressDAO.getByParam(id));
    }

    @Override
    public Optional<Address> getByPropertyId(Integer id) {
        Property property = propertyDAO.getByParam(id);
        return Optional.ofNullable(addressDAO.getByParam(property));
    }

    @Override
    public List<Address> getAll() {
        return addressDAO.getAll();
    }

    @Override
    public void updateById(Integer id, Address address) {
        AddressValidator.validate(address);
        addressDAO.updateById(id, address);
    }

    @Override
    public void deleteById(Integer id) {
        addressDAO.deleteById(id);
    }
}
