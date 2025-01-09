package senla.dao.impl;

import senla.dao.AbstractDAO;
import senla.dicontainer.annotation.Component;
import senla.model.Role;

@Component
public class RoleDAOImpl extends AbstractDAO<Role, Integer> {

    @Override
    protected Class<Role> getEntityClass() {
        return Role.class;
    }
}
