package unit.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import senla.controller.PropertyController;
import senla.dto.PropertyDto;
import senla.model.constant.PropertyType;
import senla.service.PropertyService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PropertyControllerTest {

    @Mock
    private PropertyService propertyService;

    @InjectMocks
    private PropertyController propertyController;

    private PropertyDto propertyDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        propertyDto = new PropertyDto();
        propertyDto.setId(1);
        propertyDto.setOwnerId(1);
        propertyDto.setType(PropertyType.APARTMENT);
        propertyDto.setPrice(100000.0);
        propertyDto.setRooms(3);
        propertyDto.setDescription("Test");
    }

    @Test
    void testCreateProperty() {
        when(propertyService.create(propertyDto)).thenReturn(propertyDto);

        ResponseEntity<PropertyDto> response = propertyController.createProperty(propertyDto);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Test", response.getBody().getDescription());
    }

    @Test
    void testSearchProperties() {
        when(propertyService.searchProperties(PropertyType.APARTMENT, 50000.0, 150000.0, 2, 4, "Test"))
                .thenReturn(List.of(propertyDto));

        ResponseEntity<List<PropertyDto>> response = propertyController.searchProperties(
                PropertyType.APARTMENT, 50000.0, 150000.0, 2, 4, "Test");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Test", response.getBody().get(0).getDescription());
    }

    @Test
    void testGetAllProperties() {
        when(propertyService.getAll()).thenReturn(List.of(propertyDto));

        ResponseEntity<List<PropertyDto>> response = propertyController.getAllProperties();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Test", response.getBody().get(0).getDescription());
    }

    @Test
    void testGetProperty() {
        when(propertyService.getById(1)).thenReturn(propertyDto);

        ResponseEntity<PropertyDto> response = propertyController.getProperty(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test", response.getBody().getDescription());
    }

    @Test
    void testUpdateProperty() {
        doNothing().when(propertyService).updateById(1, propertyDto);

        ResponseEntity<String> response = propertyController.updateProperty(1, propertyDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Недвижимость успешно обновлена", response.getBody());
    }

    @Test
    void testDeleteProperty() {
        doNothing().when(propertyService).deleteById(1);

        ResponseEntity<String> response = propertyController.deleteProperty(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Недвижимость успешно удалена", response.getBody());
    }
}
