package senla.service;


import senla.dto.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto create(RoleDto roleDto);

    RoleDto getById(Integer id);

    List<RoleDto> getAll();

    void updateById(Integer id, RoleDto roleDto);

    void deleteById(Integer id);
}
