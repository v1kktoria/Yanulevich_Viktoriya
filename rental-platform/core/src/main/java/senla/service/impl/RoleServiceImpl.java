package senla.service.impl;

import senla.dao.impl.RoleDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Role;
import senla.service.RoleService;
import senla.util.validator.RoleValidator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDAOImpl roleDAO;

    @Override
    public Optional<Role> create(Role role) {
        RoleValidator.validate(role);
        return Optional.ofNullable(roleDAO.create(role));
    }

    @Override
    public Optional<Role> getById(Integer id) {
        return Optional.ofNullable(roleDAO.getByParam(id));
    }

    @Override
    public List<Role> getAll() {
        return roleDAO.getAll();
    }

    @Override
    public void updateById(Integer id, Role role) {
        RoleValidator.validate(role);
        roleDAO.updateById(id, role);
    }

    @Override
    public void deleteById(Integer id) {
        roleDAO.deleteById(id);
    }
}
