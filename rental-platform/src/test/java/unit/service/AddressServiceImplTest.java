package unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import senla.dto.AddressDto;
import senla.exception.ServiceException;
import senla.model.Address;
import senla.model.Property;
import senla.repository.AddressRepository;
import senla.repository.PropertyRepository;
import senla.service.impl.AddressServiceImpl;
import senla.util.mappers.AddressMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    private AddressDto addressDto;

    private Property property;

    private Address address;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        addressDto = new AddressDto();
        addressDto.setId(1);
        addressDto.setPropertyId(1);

        property = new Property();
        property.setId(1);

        address = new Address();
        address.setId(1);
    }

    @Test
    void testCreate() {
        when(propertyRepository.findById(1)).thenReturn(Optional.of(property));
        when(addressRepository.existsByPropertyId(property.getId())).thenReturn(false);
        when(addressMapper.toEntity(addressDto, property)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);
        when(addressMapper.toDto(address)).thenReturn(addressDto);

        AddressDto createdAddress = addressService.create(addressDto);

        assertNotNull(createdAddress);
        assertEquals(1, createdAddress.getId());
        verify(addressRepository, times(1)).save(address);
    }

    @Test
    void testCreatePropertyNotFound() {
        when(propertyRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> addressService.create(addressDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
        verify(addressRepository, never()).save(any());
    }

    @Test
    void testCreateAddressAlreadyExists() {
        when(propertyRepository.findById(1)).thenReturn(Optional.of(property));
        when(addressRepository.existsByPropertyId(property.getId())).thenReturn(true);

        ServiceException exception = assertThrows(ServiceException.class, () -> addressService.create(addressDto));

        assertEquals("Адрес для недвижимости с id 1 уже существует", exception.getMessage());
        verify(addressRepository, never()).save(any());
    }

    @Test
    void testGetById() {
        when(addressRepository.findById(1)).thenReturn(Optional.of(address));
        when(addressMapper.toDto(address)).thenReturn(addressDto);

        AddressDto result = addressService.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        when(addressRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> addressService.getById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testUpdateById() {
        when(addressRepository.findById(1)).thenReturn(Optional.of(address));

        addressService.updateById(1, addressDto);

        verify(addressMapper, times(1)).updateEntity(addressDto, address);
        verify(addressRepository, times(1)).save(address);
    }

    @Test
    void testUpdateByIdNotFound() {
        when(addressRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> addressService.updateById(1, addressDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        when(addressRepository.findById(1)).thenReturn(Optional.of(address));

        addressService.deleteById(1);

        verify(addressRepository, times(1)).delete(address);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(addressRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> addressService.deleteById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }
}
