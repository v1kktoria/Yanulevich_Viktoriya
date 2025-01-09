package senla.service;

import senla.model.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    Optional<Address> create(Address address);

    Optional<Address> getById(Integer id);

    List<Address> getByPropertyId(Integer id);

    List<Address> getAll();

    void updateById(Integer id, Address address);

    void deleteById(Integer id);
}
