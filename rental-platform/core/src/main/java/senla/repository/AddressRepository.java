package senla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senla.model.Address;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    List<Address> findAllByPropertyId(Integer id);

    boolean existsByPropertyId(Integer id);
}
