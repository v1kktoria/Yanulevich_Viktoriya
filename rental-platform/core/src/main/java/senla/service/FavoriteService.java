package senla.service;

import senla.dto.PropertyDto;

import java.util.List;
import java.util.Set;

public interface FavoriteService {

    void addPropertyToFavorites(Integer userId, Integer propertyId);

    void removePropertyFromFavorites(Integer userId, Integer propertyId);

    List<PropertyDto> getFavoritesByUserId(Integer userId);
}
