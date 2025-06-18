package service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import senla.Main;
import senla.dto.FavoriteDto;
import senla.exception.ServiceException;
import senla.service.FavoriteService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = Main.class)
@RequiredArgsConstructor
@Transactional
@ActiveProfiles("test")
public class FavoriteServiceIntegrationTest {

    @Autowired
    private FavoriteService favoriteService;

    private FavoriteDto favoriteDto;

    @BeforeEach
    public void setUp() {
        favoriteDto = new FavoriteDto();
        favoriteDto.setUserId(1);
    }

    @Test
    public void testCreateFavorite() {
        FavoriteDto createdFavorite = favoriteService.create(favoriteDto);
        assertThat(createdFavorite).isNotNull();
        assertThat(createdFavorite.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetFavoriteById() {
        FavoriteDto createdFavorite = favoriteService.create(favoriteDto);
        FavoriteDto fetchedFavorite = favoriteService.getById(createdFavorite.getId());
        assertThat(fetchedFavorite).isNotNull();
        assertThat(fetchedFavorite.getId()).isEqualTo(createdFavorite.getId());
    }

    @Test
    public void testGetFavoriteByIdNotFound() {
        assertThrows(ServiceException.class, () -> favoriteService.getById(9999));
    }

    @Test
    public void testGetAllFavorites() {
        favoriteService.create(favoriteDto);
        assertThat(favoriteService.getAll()).isNotEmpty();
    }

    @Test
    public void testGetFavoritesByUserId() {
        favoriteService.create(favoriteDto);
        assertThat(favoriteService.getByUserId(1)).isNotEmpty();
    }

    @Test
    public void testUpdateFavorite() {
        FavoriteDto createdFavorite = favoriteService.create(favoriteDto);
        createdFavorite.setUserId(100);
        favoriteService.updateById(createdFavorite.getId(), createdFavorite);

        FavoriteDto updatedFavorite = favoriteService.getById(createdFavorite.getId());
        assertThat(updatedFavorite.getUserId()).isEqualTo(100);
    }

    @Test
    public void testDeleteFavorite() {
        FavoriteDto createdFavorite = favoriteService.create(favoriteDto);
        favoriteService.deleteById(createdFavorite.getId());

        assertThrows(ServiceException.class, () -> favoriteService.getById(createdFavorite.getId()));
    }
}
