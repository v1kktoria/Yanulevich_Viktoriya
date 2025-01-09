package senla.service.impl;

import senla.dao.impl.FavoriteDAOImpl;
import senla.dao.impl.UserDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Favorite;
import senla.model.User;
import senla.service.FavoriteService;
import senla.util.TransactionManager;

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
        return TransactionManager.executeInTransaction(() -> {
            return Optional.ofNullable(favoriteDAO.save(favorite));
        });
    }

    @Override
    public Optional<Favorite> getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return Optional.ofNullable(favoriteDAO.findById(id));
        });
    }

    @Override
    public List<Favorite> getByUserId(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            User user = userDAO.findById(id);
            return favoriteDAO.findByUser(user);
        });
    }

    @Override
    public List<Favorite> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            return favoriteDAO.findAll();
        });
    }

    @Override
    public void updateById(Integer id, Favorite favorite) {
        TransactionManager.executeInTransaction(() -> {
            favorite.setId(id);
            favoriteDAO.update(favorite);
            return Optional.empty();
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            favoriteDAO.deleteById(id);
            return Optional.empty();
        });
    }
}
