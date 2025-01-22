package senla.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import senla.dao.AddressDao;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Address;
import senla.service.AddressService;
import senla.util.TransactionManager;
import senla.util.validator.AddressValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;

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
            return addressDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
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
            Address address = addressDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
            addressDao.delete(address);
        });
    }
}
