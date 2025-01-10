package senla.dao;

import senla.model.User;

import java.util.List;

public interface UserDao extends ParentDao<User, Integer> {

    boolean existsByUsername(String username);

    List<User> findAllWithEssentialDetails();
}
