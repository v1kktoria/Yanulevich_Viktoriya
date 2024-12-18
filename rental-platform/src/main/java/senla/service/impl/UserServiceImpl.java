package senla.service.impl;

import senla.dao.impl.UserDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.User;
import senla.service.UserService;
import senla.util.validator.UserValidator;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAOImpl userDAO;

    @Override
    public Optional<User> create(User user) {
        UserValidator.validate(user);
        if (userDAO.existsByUsername(user.getUsername())) {
            throw new ServiceException(ServiceExceptionEnum.USER_ALREADY_EXISTS, user.getUsername());
        }
        return Optional.ofNullable(userDAO.create(user));
    }

    @Override
    public Optional<User> getById(Integer id) {
        return Optional.ofNullable(userDAO.getByParam(id));
    }

    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

    @Override
    public void updateById(Integer id, User user) {
        UserValidator.validate(user);
        if (!user.getUsername().equals(userDAO.getByParam(id).getUsername()) && userDAO.existsByUsername(user.getUsername())) {
            throw new ServiceException(ServiceExceptionEnum.USER_ALREADY_EXISTS, user.getUsername());
        }
        userDAO.updateById(id, user);
    }

    @Override
    public void deleteById(Integer id) {
        userDAO.deleteById(id);
    }
}
