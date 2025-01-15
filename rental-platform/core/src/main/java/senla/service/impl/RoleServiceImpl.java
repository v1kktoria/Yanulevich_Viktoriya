package senla.service.impl;

import senla.dao.RoleDao;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Role;
import senla.service.RoleService;
import senla.util.TransactionManager;
import senla.util.validator.RoleValidator;

import java.util.List;

@Component
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role create(Role role) {
        return TransactionManager.executeInTransaction(() -> {
            RoleValidator.validate(role);
            return roleDao.save(role);
        });
    }

    @Override
    public Role getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return roleDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        });
    }

    @Override
    public List<Role> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            return roleDao.findAll();
        });
    }

    @Override
    public void updateById(Integer id, Role role) {
        TransactionManager.executeInTransaction(() -> {
            role.setId(id);
            RoleValidator.validate(role);
            roleDao.update(role);
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            Role role = roleDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
            roleDao.delete(role);
        });
    }
}
