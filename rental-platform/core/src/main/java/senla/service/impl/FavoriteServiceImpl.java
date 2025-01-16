package senla.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import senla.dao.FavoriteDao;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Favorite;
import senla.service.FavoriteService;
import senla.util.TransactionManager;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteDao favoriteDao;

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
