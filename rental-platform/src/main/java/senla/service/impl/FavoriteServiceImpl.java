package senla.service.impl;

import senla.dao.impl.FavoriteDAOImpl;
import senla.dao.impl.UserDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Favorite;
import senla.model.User;
import senla.service.FavoriteService;

import java.util.List;

@Component
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteDAOImpl favoriteDAO;

    @Autowired
    private UserDAOImpl userDAO;

    @Override
    public Favorite create(Favorite favorite) {
        return favoriteDAO.create(favorite);
    }

    @Override
    public Favorite getById(Integer id) {
        return favoriteDAO.getByParam(id);
    }

    @Override
    public Favorite getByUserId(Integer id) {
        User user = userDAO.getByParam(id);
        return favoriteDAO.getByParam(user);
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
