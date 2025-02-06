package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dto.FavoriteDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Favorite;
import senla.repository.FavoriteRepository;
import senla.service.FavoriteService;
import senla.util.mappers.FavoriteMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    private final FavoriteMapper favoriteMapper;

    @Transactional
    @Override
    public FavoriteDto create(FavoriteDto favoriteDto) {
        Favorite favorite = favoriteMapper.toEntity(favoriteDto);
        FavoriteDto createdFavorite = favoriteMapper.toDto(favoriteRepository.save(favorite));
        log.info("Избранное успешно добавлено с ID: {}", createdFavorite.getId());
        return createdFavorite;
    }

    @Override
    public FavoriteDto getById(Integer id) {
        FavoriteDto favorite = favoriteMapper.toDto(favoriteRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id)));
        log.info("Избранное успешно получено: {}", favorite);
        return favorite;
    }

    @Override
    public List<FavoriteDto> getByUserId(Integer id) {
        List<Favorite> favorites = favoriteRepository.findAllByUserId(id);
        List<FavoriteDto> favoriteDtos = favorites.stream()
                .map(favoriteMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} избранных для пользователя с ID: {}", favoriteDtos.size(), id);
        return favoriteDtos;
    }

    @Override
    public List<FavoriteDto> getAll() {
        List<Favorite> favorites = favoriteRepository.findAll();
        List<FavoriteDto> favoriteDtos = favorites.stream()
                .map(favoriteMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} избранных", favoriteDtos.size());
        return favoriteDtos;
    }

    @Transactional
    @Override
    public void updateById(Integer id, FavoriteDto favoriteDto) {
        Favorite favorite = favoriteRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        favoriteDto.setId(id);
        favoriteMapper.updateEntity(favoriteDto, favorite);
        favoriteRepository.save(favorite);
        log.info("Избранное с ID: {} успешно обновлено", id);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        Favorite favorite = favoriteRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        favoriteRepository.delete(favorite);
        log.info("Избранное с ID: {} успешно удалено", id);
    }
}
