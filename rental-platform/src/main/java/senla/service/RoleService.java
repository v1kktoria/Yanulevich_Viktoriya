package senla.service;


import senla.model.Role;

import java.util.List;

public interface RoleService {

    Role create(Role role);

    Role getById(Integer id);

    List<Role> getAll();

    void updateById(Integer id, Role role);

    void deleteById(Integer id);
}
