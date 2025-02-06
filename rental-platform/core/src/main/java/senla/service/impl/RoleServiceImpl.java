package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dao.RoleDao;
import senla.dto.RoleDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Role;
import senla.service.RoleService;
import senla.util.mappers.RoleMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@MeasureExecutionTime
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    private final RoleMapper roleMapper;

    @Override
    public RoleDto create(RoleDto roleDto) {
        Role role = roleMapper.toEntity(roleDto);
        Role savedRole = roleDao.save(role);
        log.info("Роль с ID: {} успешно создана", savedRole.getId());
        return roleMapper.toDto(savedRole);
    }

    @Override
    public RoleDto getById(Integer id) {
        Role role = roleDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        log.info("Роль с ID: {} успешно получена", id);
        return roleMapper.toDto(role);
    }

    @Override
    public List<RoleDto> getAll() {
        List<Role> roles = roleDao.findAll();
        log.info("Найдено {} ролей", roles.size());
        return roles.stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateById(Integer id, RoleDto roleDto) {
        Role role = roleDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        roleDto.setId(id);
        roleMapper.updateEntity(roleDto, role);
        roleDao.update(role);
        log.info("Роль с ID: {} успешно обновлена", id);
    }

    @Override
    public void deleteById(Integer id) {
        Role role = roleDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        roleDao.delete(role);
        log.info("Роль с ID: {} успешно удалена", id);
    }
}
