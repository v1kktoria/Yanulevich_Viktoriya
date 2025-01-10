package senla.service.impl;

import senla.dao.AddressDao;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Address;
import senla.service.AddressService;
import senla.util.TransactionManager;
import senla.util.validator.AddressValidator;

import java.util.List;

@Component
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Override
    public Address create(Address address) {
        return TransactionManager.executeInTransaction(() -> {
            AddressValidator.validate(address);
            return addressDao.save(address);
        });
    }

    @Override
    public Address getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return addressDao.findById(id);
        });
    }

    @Override
    public List<Address> getByPropertyId(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return addressDao.findByPropertyId(id);
        });
    }

    @Override
    public List<Address> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            return addressDao.findAll();
        });
    }

    @Override
    public void updateById(Integer id, Address address) {
        TransactionManager.executeInTransaction(() -> {
            address.setId(id);
            AddressValidator.validate(address);
            addressDao.update(address);
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            addressDao.deleteById(id);
        });
    }
}
