package senla.service;

import senla.model.Role;
import senla.model.User;
import senla.model.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserRoleService {

    void create(UserRole userRole);

    Optional<UserRole> getByUserAndRole(User user, Role role);

    List<UserRole> getAll();

    void deleteByUserAndRole(User user, Role role);
}
