package senla.util.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import senla.dto.FavoriteDto;
import senla.dto.PropertyDto;
import senla.model.Favorite;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FavoriteMapper {

    private final ModelMapper modelMapper;

    public FavoriteDto toDto(Favorite favorite) {
        FavoriteDto favoriteDto = modelMapper.map(favorite, FavoriteDto.class);

        favoriteDto.setProperties(favorite.getProperty() != null ? favorite.getProperty().stream()
                .map(property -> modelMapper.map(property, PropertyDto.class))
                .collect(Collectors.toSet()) : new HashSet<>());

        return favoriteDto;
    }

    public Favorite toEntity(FavoriteDto favoriteDto) {
        return modelMapper.map(favoriteDto, Favorite.class);
    }

    public void updateEntity(FavoriteDto favoriteDto, Favorite favorite) {
        modelMapper.map(favoriteDto, favorite);
    }
}
