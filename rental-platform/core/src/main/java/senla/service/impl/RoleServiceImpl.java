package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dto.RoleDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Role;
import senla.repository.RoleRepository;
import senla.service.RoleService;
import senla.util.mappers.RoleMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Transactional
    @Override
    public RoleDto create(RoleDto roleDto) {
        Role role = roleMapper.toEntity(roleDto);
        Role savedRole = roleRepository.save(role);
        log.info("Роль с ID: {} успешно создана", savedRole.getId());
        return roleMapper.toDto(savedRole);
    }

    @Override
    public RoleDto getById(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        log.info("Роль с ID: {} успешно получена", id);
        return roleMapper.toDto(role);
    }

    @Override
    public List<RoleDto> getAll() {
        List<Role> roles = roleRepository.findAll();
        log.info("Найдено {} ролей", roles.size());
        return roles.stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateById(Integer id, RoleDto roleDto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        roleDto.setId(id);
        roleMapper.updateEntity(roleDto, role);
        roleRepository.save(role);
        log.info("Роль с ID: {} успешно обновлена", id);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        roleRepository.delete(role);
        log.info("Роль с ID: {} успешно удалена", id);
    }
}
