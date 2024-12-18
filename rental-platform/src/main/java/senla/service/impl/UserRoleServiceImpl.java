package senla.service.impl;

import senla.dao.UserRoleDAO;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Role;
import senla.model.User;
import senla.model.UserRole;
import senla.service.UserRoleService;

import java.util.List;

@Component
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Override
    public void create(UserRole userRole) {
        userRoleDAO.create(userRole);
    }

    @Override
    public UserRole getByUserAndRole(User user, Role role) {
        return userRoleDAO.getByUserAndRole(user, role);
    }

    @Override
    public List<UserRole> getAll() {
        return userRoleDAO.getAll();
    }

    @Override
    public void deleteByUserAndRole(User user, Role role) {
        userRoleDAO.deleteByUserAndRole(user, role);
    }
}
