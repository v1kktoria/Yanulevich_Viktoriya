package senla.service.impl;

import senla.dao.FavoriteDao;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Favorite;
import senla.service.FavoriteService;
import senla.util.TransactionManager;

import java.util.List;

@Component
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteDao favoriteDao;

    @Override
    public Favorite create(Favorite favorite) {
        return TransactionManager.executeInTransaction(() -> {
            return favoriteDao.save(favorite);
        });
    }

    @Override
    public Favorite getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return favoriteDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        });
    }

    @Override
    public List<Favorite> getByUserId(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return favoriteDao.findByUserId(id);
        });
    }

    @Override
    public List<Favorite> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            return favoriteDao.findAll();
        });
    }

    @Override
    public void updateById(Integer id, Favorite favorite) {
        TransactionManager.executeInTransaction(() -> {
            favorite.setId(id);
            favoriteDao.update(favorite);
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            Favorite favorite = favoriteDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
            favoriteDao.delete(favorite);
        });
    }
}
