package senla.service;

import senla.model.Favorite;

import java.util.List;
import java.util.Optional;

public interface FavoriteService {

    Favorite create(Favorite favorite);

    Favorite getById(Integer id);

    List<Favorite> getByUserId(Integer id);

    List<Favorite> getAll();

    void updateById(Integer id, Favorite favorite);

    void deleteById(Integer id);
}
