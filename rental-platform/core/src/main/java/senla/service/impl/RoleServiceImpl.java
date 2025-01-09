package senla.service.impl;

import senla.dao.impl.RoleDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Role;
import senla.service.RoleService;
import senla.util.TransactionManager;
import senla.util.validator.RoleValidator;

import java.util.List;
import java.util.Optional;

@Component
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDAOImpl roleDAO;

    @Override
    public Optional<Role> create(Role role) {
        return TransactionManager.executeInTransaction(() -> {
            RoleValidator.validate(role);
            return Optional.ofNullable(roleDAO.save(role));
        });
    }

    @Override
    public Optional<Role> getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return Optional.ofNullable(roleDAO.findById(id));
        });
    }

    @Override
    public List<Role> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            return roleDAO.findAll();
        });
    }

    @Override
    public void updateById(Integer id, Role role) {
        TransactionManager.executeInTransaction(() -> {
            role.setId(id);
            RoleValidator.validate(role);
            roleDAO.update(role);
            return Optional.empty();
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            roleDAO.deleteById(id);
            return Optional.empty();
        });
    }
}
