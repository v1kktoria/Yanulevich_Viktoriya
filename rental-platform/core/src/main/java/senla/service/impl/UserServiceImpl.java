package senla.service.impl;

import senla.dao.impl.UserDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.User;
import senla.service.UserService;
import senla.util.TransactionManager;
import senla.util.validator.UserValidator;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAOImpl userDAO;

    @Override
    public Optional<User> create(User user) {
        return TransactionManager.executeInTransaction(() -> {
            UserValidator.validate(user);
            if (userDAO.existsByUsername(user.getUsername())) {
                throw new ServiceException(ServiceExceptionEnum.USER_ALREADY_EXISTS, user.getUsername());
            }
            return Optional.ofNullable(userDAO.save(user));
        });
    }

    @Override
    public Optional<User> getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return Optional.ofNullable(userDAO.findById(id));
        });
    }

    @Override
    public List<User> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            return userDAO.findAll();
        });
    }

    @Override
    public void updateById(Integer id, User user) {
        TransactionManager.executeInTransaction(() -> {
            user.setId(id);
            UserValidator.validate(user);
            if (!user.getUsername().equals(userDAO.findById(id).getUsername()) && userDAO.existsByUsername(user.getUsername())) {
                throw new ServiceException(ServiceExceptionEnum.USER_ALREADY_EXISTS, user.getUsername());
            }
            userDAO.update(user);
            return Optional.empty();
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            userDAO.deleteById(id);
            return Optional.empty();
        });
    }

    @Override
    public List<User> getAllWithEssentialDetails() {
        return TransactionManager.executeInTransaction(() -> {
            return userDAO.findAllWithEssentialDetails();
        });
    }
}
