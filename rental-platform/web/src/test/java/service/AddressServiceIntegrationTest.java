package service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import senla.Main;
import senla.dto.AddressDto;
import senla.exception.ServiceException;
import senla.service.AddressService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = Main.class)
@RequiredArgsConstructor
@Transactional
@ActiveProfiles("test")
public class AddressServiceIntegrationTest {

    @Autowired
    private AddressService addressService;

    private AddressDto addressDto;

    @BeforeEach
    public void setUp() {
        addressDto = new AddressDto();
        addressDto.setPropertyId(1);
        addressDto.setStreet("Test Street");
        addressDto.setCity("Test City");
    }

    @Test
    public void testCreateAddress() {
        AddressDto createdAddress = addressService.create(addressDto);
        assertThat(createdAddress).isNotNull();
        assertThat(createdAddress.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetAddressById() {
        AddressDto createdAddress = addressService.create(addressDto);
        AddressDto fetchedAddress = addressService.getById(createdAddress.getId());
        assertThat(fetchedAddress).isNotNull();
        assertThat(fetchedAddress.getId()).isEqualTo(createdAddress.getId());
    }

    @Test
    public void testGetAddressByIdNotFound() {
        assertThrows(ServiceException.class, () -> addressService.getById(9999));
    }

    @Test
    public void testGetAllAddresses() {
        addressService.create(addressDto);
        assertThat(addressService.getAll()).isNotEmpty();
    }

    @Test
    public void testGetAddressesByPropertyId() {
        addressService.create(addressDto);
        assertThat(addressService.getByPropertyId(1)).isNotEmpty();
    }

    @Test
    public void testUpdateAddress() {
        AddressDto createdAddress = addressService.create(addressDto);
        createdAddress.setStreet("Updated Street");
        addressService.updateById(createdAddress.getId(), createdAddress);

        AddressDto updatedAddress = addressService.getById(createdAddress.getId());
        assertThat(updatedAddress.getStreet()).isEqualTo("Updated Street");
    }

    @Test
    public void testDeleteAddress() {
        AddressDto createdAddress = addressService.create(addressDto);
        addressService.deleteById(createdAddress.getId());

        assertThrows(ServiceException.class, () -> addressService.getById(createdAddress.getId()));
    }
}