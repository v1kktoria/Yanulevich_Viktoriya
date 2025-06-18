package unit.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import senla.controller.AddressController;
import senla.dto.AddressDto;
import senla.service.AddressService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AddressControllerTest {

    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

    private AddressDto addressDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        addressDto = new AddressDto();
        addressDto.setId(1);
        addressDto.setStreet("Main Street");
        addressDto.setCity("Test City");
    }

    @Test
    void testCreateAddress() {
        when(addressService.create(addressDto)).thenReturn(addressDto);

        ResponseEntity<AddressDto> response = addressController.createAddress(addressDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Main Street", response.getBody().getStreet());
    }

    @Test
    void testGetAllAddresses() {
        when(addressService.getAll()).thenReturn(List.of(addressDto));

        ResponseEntity<List<AddressDto>> response = addressController.getAllAddresses();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetAddress() {
        when(addressService.getById(1)).thenReturn(addressDto);

        ResponseEntity<AddressDto> response = addressController.getAddress(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Main Street", response.getBody().getStreet());
    }

    @Test
    void testUpdateAddress() {
        doNothing().when(addressService).updateById(1, addressDto);

        ResponseEntity<String> response = addressController.updateAddress(1, addressDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Адрес успешно обновлен", response.getBody());
    }

    @Test
    void testDeleteAddress() {
        doNothing().when(addressService).deleteById(1);

        ResponseEntity<String> response = addressController.deleteAddress(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Адрес успешно удален", response.getBody());
    }
}
