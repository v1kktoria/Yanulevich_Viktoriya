package senla.service.impl;

import senla.dao.impl.FavoriteDAOImpl;
import senla.dao.impl.UserDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Favorite;
import senla.model.User;
import senla.service.FavoriteService;

import java.util.List;
import java.util.Optional;

@Component
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteDAOImpl favoriteDAO;

    @Autowired
    private UserDAOImpl userDAO;

    @Override
    public Optional<Favorite> create(Favorite favorite) {
        return Optional.ofNullable(favoriteDAO.create(favorite));
    }

    @Override
    public Optional<Favorite> getById(Integer id) {
        return Optional.ofNullable(favoriteDAO.getByParam(id));
    }

    @Override
    public Optional<Favorite> getByUserId(Integer id) {
        User user = userDAO.getByParam(id);
        return Optional.ofNullable(favoriteDAO.getByParam(user));
    }

    @Override
    public List<Favorite> getAll() {
        return favoriteDAO.getAll();
    }

    @Override
    public void updateById(Integer id, Favorite favorite) {
        favoriteDAO.updateById(id, favorite);
    }

    @Override
    public void deleteById(Integer id) {
        favoriteDAO.deleteById(id);
    }
}
