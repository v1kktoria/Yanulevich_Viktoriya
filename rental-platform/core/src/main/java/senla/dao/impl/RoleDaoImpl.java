package senla.dao.impl;

import org.springframework.stereotype.Repository;
import senla.dao.AbstractDao;
import senla.dao.RoleDao;
import senla.model.Role;

@Repository
public class RoleDaoImpl extends AbstractDao<Role, Integer> implements RoleDao {

    @Override
    protected Class<Role> getEntityClass() {
        return Role.class;
    }
}
