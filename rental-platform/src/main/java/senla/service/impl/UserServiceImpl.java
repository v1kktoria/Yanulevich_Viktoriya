package senla.service.impl;

import senla.dao.impl.UserDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.User;
import senla.service.UserService;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAOImpl userDAO;

    @Override
    public User create(User user) {
        validate(user);
        if (userDAO.existsByUsername(user.getUsername())) {
            throw new ServiceException(ServiceExceptionEnum.USER_ALREADY_EXISTS, user.getUsername());
        }
        return userDAO.create(user);
    }

    @Override
    public User getById(Integer id) {
        return userDAO.getByParam(id);
    }

    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

    @Override
    public void updateById(Integer id, User user) {
        validate(user);
        if (!user.getUsername().equals(userDAO.getByParam(id).getUsername()) && userDAO.existsByUsername(user.getUsername())) {
            throw new ServiceException(ServiceExceptionEnum.USER_ALREADY_EXISTS, user.getUsername());
        }
        userDAO.updateById(id, user);
    }

    @Override
    public void deleteById(Integer id) {
        userDAO.deleteById(id);
    }

    private void validate(User user) {
        if (user.getUsername().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Имя пользователя не может быть пустым");
        }

        if (user.getPassword().isEmpty() || user.getPassword().length() < 8) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Пароль должен быть не меньше 8 символов");
        }
    }
}
