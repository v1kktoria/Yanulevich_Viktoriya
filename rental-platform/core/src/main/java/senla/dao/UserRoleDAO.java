package senla.dao;

import senla.model.Role;
import senla.model.User;
import senla.model.UserRole;

import java.util.List;

public interface UserRoleDAO {
    void create(UserRole userRole);
    UserRole getByUserAndRole(User user, Role role);
    List<UserRole> getAll();
    void deleteByUserAndRole(User user, Role role);
}