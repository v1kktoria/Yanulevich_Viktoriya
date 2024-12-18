package senla.service;

import senla.model.Address;

import java.util.List;

public interface AddressService {

    Address create(Address address);

    Address getById(Integer id);

    Address getByPropertyId(Integer id);

    List<Address> getAll();

    void updateById(Integer id, Address address);

    void deleteById(Integer id);
}
