package senla.service;


import senla.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Optional<Role> create(Role role);

    Optional<Role> getById(Integer id);

    List<Role> getAll();

    void updateById(Integer id, Role role);

    void deleteById(Integer id);
}
