package senla.dao.impl;

import senla.dao.AbstractDao;
import senla.dao.RoleDao;
import senla.dicontainer.annotation.Component;
import senla.model.Role;

@Component
public class RoleDaoImpl extends AbstractDao<Role, Integer> implements RoleDao {

    @Override
    protected Class<Role> getEntityClass() {
        return Role.class;
    }
}
