package senla.service;


import senla.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> create(User user);

    Optional<User> getById(Integer id);

    List<User> getAll();

    void updateById(Integer id, User user);

    void deleteById(Integer id);
}
