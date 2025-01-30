package senla.service;

import senla.dto.FavoriteDto;

import java.util.List;

public interface FavoriteService {

    FavoriteDto create(FavoriteDto favoriteDto);

    FavoriteDto getById(Integer id);

    List<FavoriteDto> getByUserId(Integer id);

    List<FavoriteDto> getAll();

    void updateById(Integer id, FavoriteDto favoriteDto);

    void deleteById(Integer id);
}
