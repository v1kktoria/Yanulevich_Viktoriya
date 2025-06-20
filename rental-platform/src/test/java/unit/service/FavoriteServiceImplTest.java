package unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import senla.dto.PropertyDto;
import senla.exception.ServiceException;
import senla.model.Favorite;
import senla.model.Property;
import senla.repository.FavoriteRepository;
import senla.repository.PropertyRepository;
import senla.repository.UserRepository;
import senla.service.impl.FavoriteServiceImpl;
import senla.util.mappers.PropertyMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FavoriteServiceImplTest {

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private PropertyMapper propertyMapper;

    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    private Integer userId = 1;

    private Integer propertyId = 100;

    private Property property;
    private Favorite favorite;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        property = new Property();
        property.setId(propertyId);

        favorite = new Favorite();
        favorite.setUser(userRepository.getReferenceById(userId));
        favorite.setProperty(new ArrayList<>());
    }

    @Test
    void testAddPropertyToFavorites() {
        when(favoriteRepository.findByUserId(userId)).thenReturn(Optional.of(favorite));
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));

        favoriteService.addPropertyToFavorites(userId, propertyId);

        assertTrue(favorite.getProperty().contains(property));
        verify(favoriteRepository, times(1)).save(favorite);
    }

    @Test
    void testAddPropertyToFavoritesPropertyNotFound() {
        when(favoriteRepository.findByUserId(userId)).thenReturn(Optional.of(favorite));
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> favoriteService.addPropertyToFavorites(userId, propertyId));

        assertEquals("Объект с ID 100 не найден", exception.getMessage());
    }

    @Test
    void testAddPropertyToFavoritesAlreadyExists() {
        when(favoriteRepository.findByUserId(userId)).thenReturn(Optional.of(favorite));
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));

        favorite.getProperty().add(property);

        ServiceException exception = assertThrows(ServiceException.class, () -> favoriteService.addPropertyToFavorites(userId, propertyId));

        assertEquals("Недвижимость с id 100 уже добавлена в избранное", exception.getMessage());
    }

    @Test
    void testRemovePropertyFromFavorites() {
        when(favoriteRepository.findByUserId(userId)).thenReturn(Optional.of(favorite));
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));

        favorite.getProperty().add(property);

        favoriteService.removePropertyFromFavorites(userId, propertyId);

        assertFalse(favorite.getProperty().contains(property));
        verify(favoriteRepository, times(1)).save(favorite);
    }

    @Test
    void testRemovePropertyFromFavoritesPropertyNotFound() {
        when(favoriteRepository.findByUserId(userId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> favoriteService.removePropertyFromFavorites(userId, propertyId));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testGetFavoritesByUserId() {
        when(favoriteRepository.findByUserId(userId)).thenReturn(Optional.of(favorite));
        when(propertyMapper.toDto(property)).thenReturn(new PropertyDto());

        List<PropertyDto> favorites = favoriteService.getFavoritesByUserId(userId);

        assertNotNull(favorites);
        assertTrue(favorites.isEmpty());
    }

    @Test
    void testGetFavoritesByUserIdNotFound() {
        when(favoriteRepository.findByUserId(userId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> favoriteService.getFavoritesByUserId(userId));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }
}
