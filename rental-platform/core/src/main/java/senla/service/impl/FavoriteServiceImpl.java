package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dto.PropertyDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Favorite;
import senla.model.Property;
import senla.repository.FavoriteRepository;
import senla.repository.PropertyRepository;
import senla.repository.UserRepository;
import senla.service.FavoriteService;
import senla.util.mappers.PropertyMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    private final UserRepository userRepository;

    private final PropertyRepository propertyRepository;

    private final PropertyMapper propertyMapper;

    @Transactional
    @Override
    public void addPropertyToFavorites(Integer userId, Integer propertyId) {
        Favorite favorite = favoriteRepository.findByUserId(userId)
                .orElseGet(() -> new Favorite(userRepository.getReferenceById(userId), new ArrayList<>()));

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, propertyId));

        if (favorite.getProperty().contains(property)) {
            throw new ServiceException(ServiceExceptionEnum.PROPERTY_ALREADY_EXIST, propertyId);
        }

        favorite.getProperty().add(property);
        favoriteRepository.save(favorite);
        log.info("Объявление {} добавлено в избранное пользователя {}", propertyId, userId);
    }

    @Transactional
    @Override
    public void removePropertyFromFavorites(Integer userId, Integer propertyId) {
        Favorite favorite = favoriteRepository.findByUserId(userId)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, userId));

        favorite.getProperty().removeIf(p -> p.getId().equals(propertyId));
        favoriteRepository.save(favorite);
        log.info("Объявление {} удалено из избранного пользователя {}", propertyId, userId);
    }

    @Override
    public List<PropertyDto> getFavoritesByUserId(Integer userId) {
        Favorite favorite = favoriteRepository.findByUserId(userId)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, userId));

        return favorite.getProperty().stream()
                .map(propertyMapper::toDto)
                .collect(Collectors.toList());
    }
}
