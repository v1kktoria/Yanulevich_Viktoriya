package senla.service.impl;

import senla.dao.impl.RoleDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Role;
import senla.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDAOImpl roleDAO;

    @Override
    public Role create(Role role) {
        validate(role);
        return roleDAO.create(role);
    }

    @Override
    public Role getById(Integer id) {
        return roleDAO.getByParam(id);
    }

    @Override
    public List<Role> getAll() {
        return roleDAO.getAll();
    }

    @Override
    public void updateById(Integer id, Role role) {
        validate(role);
        roleDAO.updateById(id, role);
    }

    @Override
    public void deleteById(Integer id) {
        roleDAO.deleteById(id);
    }

    private void validate(Role role) {
        if (role.getRoleName().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Имя роли не может быть пустым");
        }
    }
}
