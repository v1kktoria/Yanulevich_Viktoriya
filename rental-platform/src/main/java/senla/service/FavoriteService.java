package senla.service;

import senla.model.Favorite;

import java.util.List;

public interface FavoriteService {

    Favorite create(Favorite favorite);

    Favorite getById(Integer id);

    Favorite getByUserId(Integer id);

    List<Favorite> getAll();

    void updateById(Integer id, Favorite favorite);

    void deleteById(Integer id);
}
