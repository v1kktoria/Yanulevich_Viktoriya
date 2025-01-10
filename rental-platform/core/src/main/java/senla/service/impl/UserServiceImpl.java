package senla.service.impl;

import senla.dao.UserDao;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.User;
import senla.service.UserService;
import senla.util.TransactionManager;
import senla.util.validator.UserValidator;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User create(User user) {
        return TransactionManager.executeInTransaction(() -> {
            UserValidator.validate(user);
            if (userDao.existsByUsername(user.getUsername())) {
                throw new ServiceException(ServiceExceptionEnum.USER_ALREADY_EXISTS, user.getUsername());
            }
            return userDao.save(user);
        });
    }

    @Override
    public User getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return userDao.findById(id);
        });
    }

    @Override
    public List<User> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            return userDao.findAll();
        });
    }

    @Override
    public void updateById(Integer id, User user) {
        TransactionManager.executeInTransaction(() -> {
            user.setId(id);
            UserValidator.validate(user);
            if (!user.getUsername().equals(userDao.findById(id).getUsername()) && userDao.existsByUsername(user.getUsername())) {
                throw new ServiceException(ServiceExceptionEnum.USER_ALREADY_EXISTS, user.getUsername());
            }
            userDao.update(user);
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            userDao.deleteById(id);
        });
    }

    @Override
    public List<User> getAllWithEssentialDetails() {
        return TransactionManager.executeInTransaction(() -> {
            return userDao.findAllWithEssentialDetails();
        });
    }
}
