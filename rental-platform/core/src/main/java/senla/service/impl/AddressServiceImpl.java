package senla.service.impl;

import senla.dao.impl.AddressDAOImpl;
import senla.dao.impl.PropertyDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Address;
import senla.model.Property;
import senla.service.AddressService;
import senla.util.TransactionManager;
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
        return TransactionManager.executeInTransaction(() -> {
            AddressValidator.validate(address);
            return Optional.ofNullable(addressDAO.save(address));
        });
    }

    @Override
    public Optional<Address> getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return Optional.ofNullable(addressDAO.findById(id));
        });
    }

    @Override
    public List<Address> getByPropertyId(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            Property property = propertyDAO.findById(id);
            return addressDAO.findByProperty(property);
        });
    }

    @Override
    public List<Address> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            return addressDAO.findAll();
        });
    }

    @Override
    public void updateById(Integer id, Address address) {
        TransactionManager.executeInTransaction(() -> {
            address.setId(id);
            AddressValidator.validate(address);
            addressDAO.update(address);
            return Optional.empty();
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            addressDAO.deleteById(id);
            return Optional.empty();
        });
    }
}
