package unit.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import senla.controller.FavoriteController;
import senla.dto.PropertyDto;
import senla.service.FavoriteService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FavoriteControllerTest {

    @Mock
    private FavoriteService favoriteService;

    @InjectMocks
    private FavoriteController favoriteController;

    private List<PropertyDto> favoriteProperties;

    private PropertyDto propertyDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        propertyDto = new PropertyDto();
        propertyDto.setId(1);
        propertyDto.setDescription("Test");

        favoriteProperties = new ArrayList<>();
        favoriteProperties.add(propertyDto);
    }

    @Test
    void testAddToFavorites() {
        doNothing().when(favoriteService).addPropertyToFavorites(1, 1);

        ResponseEntity<String> response = favoriteController.addToFavorites(1, 1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Недвижимость добавлена в избранное", response.getBody());
    }

    @Test
    void testRemoveFromFavorites() {
        doNothing().when(favoriteService).removePropertyFromFavorites(1, 1);

        ResponseEntity<String> response = favoriteController.removeFromFavorites(1, 1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Недвижимость удалена из избранного", response.getBody());
    }

    @Test
    void testGetFavorites() {
        when(favoriteService.getFavoritesByUserId(1)).thenReturn(favoriteProperties);

        ResponseEntity<List<PropertyDto>> response = favoriteController.getFavorites(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Test", response.getBody().iterator().next().getDescription());
    }
}
