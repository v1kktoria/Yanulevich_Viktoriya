package senla.dao;

import senla.model.Favorite;

import java.util.List;

public interface FavoriteDao extends ParentDao<Favorite, Integer> {
    public List<Favorite> findByUserId(Integer id);
}
