package senla.dao;

import senla.model.Address;

import java.util.List;

public interface AddressDao extends ParentDao<Address, Integer>{
    List<Address> findByPropertyId(Integer id);
}
