package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dao.FavoriteDao;
import senla.dto.FavoriteDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Favorite;
import senla.service.FavoriteService;
import senla.util.mappers.FavoriteMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@MeasureExecutionTime
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteDao favoriteDao;

    private final FavoriteMapper favoriteMapper;

    @Override
    public FavoriteDto create(FavoriteDto favoriteDto) {
        Favorite favorite = favoriteMapper.toEntity(favoriteDto);
        FavoriteDto createdFavorite = favoriteMapper.toDto(favoriteDao.save(favorite));
        log.info("Избранное успешно добавлено с ID: {}", createdFavorite.getId());
        return createdFavorite;
    }

    @Override
    public FavoriteDto getById(Integer id) {
        FavoriteDto favorite = favoriteMapper.toDto(favoriteDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id)));
        log.info("Избранное успешно получено: {}", favorite);
        return favorite;
    }

    @Override
    public List<FavoriteDto> getByUserId(Integer id) {
        List<Favorite> favorites = favoriteDao.findByUserId(id);
        List<FavoriteDto> favoriteDtos = favorites.stream()
                .map(favoriteMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} избранных для пользователя с ID: {}", favoriteDtos.size(), id);
        return favoriteDtos;
    }

    @Override
    public List<FavoriteDto> getAll() {
        List<Favorite> favorites = favoriteDao.findAll();
        List<FavoriteDto> favoriteDtos = favorites.stream()
                .map(favoriteMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} избранных", favoriteDtos.size());
        return favoriteDtos;
    }

    @Override
    public void updateById(Integer id, FavoriteDto favoriteDto) {
        Favorite favorite = favoriteDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        favoriteDto.setId(id);
        favoriteMapper.updateEntity(favoriteDto, favorite);
        favoriteDao.update(favorite);
        log.info("Избранное с ID: {} успешно обновлено", id);
    }

    @Override
    public void deleteById(Integer id) {
        Favorite favorite = favoriteDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        favoriteDao.delete(favorite);
        log.info("Избранное с ID: {} успешно удалено", id);
    }
}
