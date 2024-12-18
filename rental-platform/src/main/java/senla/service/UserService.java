package senla.service;


import senla.model.User;

import java.util.List;

public interface UserService {

    User create(User user);

    User getById(Integer id);

    List<User> getAll();

    void updateById(Integer id, User user);

    void deleteById(Integer id);
}
