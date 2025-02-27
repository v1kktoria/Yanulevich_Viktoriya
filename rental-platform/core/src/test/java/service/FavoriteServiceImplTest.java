package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import senla.dto.FavoriteDto;
import senla.exception.ServiceException;
import senla.model.Favorite;
import senla.repository.FavoriteRepository;
import senla.service.impl.FavoriteServiceImpl;
import senla.util.mappers.FavoriteMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FavoriteServiceImplTest {

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private FavoriteMapper favoriteMapper;

    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    private FavoriteDto favoriteDto;

    private Favorite favorite;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        favoriteDto = new FavoriteDto();
        favoriteDto.setId(1);

        favorite = new Favorite();
        favorite.setId(1);
    }

    @Test
    void testCreate() {
        when(favoriteMapper.toEntity(favoriteDto)).thenReturn(favorite);
        when(favoriteRepository.save(favorite)).thenReturn(favorite);
        when(favoriteMapper.toDto(favorite)).thenReturn(favoriteDto);

        FavoriteDto createdFavorite = favoriteService.create(favoriteDto);

        assertNotNull(createdFavorite);
        assertEquals(1, createdFavorite.getId());
        verify(favoriteRepository, times(1)).save(favorite);
    }

    @Test
    void testGetById() {
        when(favoriteRepository.findById(1)).thenReturn(Optional.of(favorite));
        when(favoriteMapper.toDto(favorite)).thenReturn(favoriteDto);

        FavoriteDto result = favoriteService.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        when(favoriteRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> favoriteService.getById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testUpdateById() {
        when(favoriteRepository.findById(1)).thenReturn(Optional.of(favorite));

        favoriteService.updateById(1, favoriteDto);

        verify(favoriteMapper, times(1)).updateEntity(favoriteDto, favorite);
        verify(favoriteRepository, times(1)).save(favorite);
    }

    @Test
    void testUpdateByIdNotFound() {
        when(favoriteRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> favoriteService.updateById(1, favoriteDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        when(favoriteRepository.findById(1)).thenReturn(Optional.of(favorite));

        favoriteService.deleteById(1);

        verify(favoriteRepository, times(1)).delete(favorite);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(favoriteRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> favoriteService.deleteById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }
}
