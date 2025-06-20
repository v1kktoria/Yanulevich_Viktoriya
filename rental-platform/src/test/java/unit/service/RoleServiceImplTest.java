package unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import senla.dto.RoleDto;
import senla.exception.ServiceException;
import senla.model.Role;
import senla.repository.RoleRepository;
import senla.service.impl.RoleServiceImpl;
import senla.util.mappers.RoleMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleServiceImpl roleService;

    private RoleDto roleDto;

    private Role role;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        roleDto = new RoleDto();
        roleDto.setId(1);
        roleDto.setRoleName("Admin");

        role = new Role();
        role.setId(1);
        role.setRoleName("Admin");
    }

    @Test
    void testCreate() {
        when(roleMapper.toEntity(roleDto)).thenReturn(role);
        when(roleRepository.save(role)).thenReturn(role);
        when(roleMapper.toDto(role)).thenReturn(roleDto);

        RoleDto createdRole = roleService.create(roleDto);

        assertNotNull(createdRole);
        assertEquals("Admin", createdRole.getRoleName());
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void testGetById() {
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));
        when(roleMapper.toDto(role)).thenReturn(roleDto);

        RoleDto result = roleService.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Admin", result.getRoleName());
    }

    @Test
    void testGetByIdNotFound() {
        when(roleRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> roleService.getById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testGetAll() {
        List<Role> roles = List.of(role);
        when(roleRepository.findAll()).thenReturn(roles);
        when(roleMapper.toDto(role)).thenReturn(roleDto);

        List<RoleDto> result = roleService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateById() {
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));

        roleDto.setId(1);
        roleService.updateById(1, roleDto);

        verify(roleMapper, times(1)).updateEntity(roleDto, role);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void testUpdateByIdNotFound() {
        when(roleRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> roleService.updateById(1, roleDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));

        roleService.deleteById(1);

        verify(roleRepository, times(1)).delete(role);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(roleRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> roleService.deleteById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }
}
