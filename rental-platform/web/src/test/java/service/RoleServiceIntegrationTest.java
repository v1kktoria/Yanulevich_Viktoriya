package service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import senla.Main;
import senla.dto.RoleDto;
import senla.exception.ServiceException;
import senla.service.RoleService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = Main.class)
@RequiredArgsConstructor
@Transactional
@ActiveProfiles("test")
public class RoleServiceIntegrationTest {

    @Autowired
    private RoleService roleService;

    private RoleDto roleDto;

    @BeforeEach
    public void setUp() {
        roleDto = new RoleDto();
        roleDto.setRoleName("ADMIN");
    }

    @Test
    public void testCreateRole() {
        RoleDto createdRole = roleService.create(roleDto);
        assertThat(createdRole).isNotNull();
        assertThat(createdRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetRoleById() {
        RoleDto createdRole = roleService.create(roleDto);
        RoleDto fetchedRole = roleService.getById(createdRole.getId());
        assertThat(fetchedRole).isNotNull();
        assertThat(fetchedRole.getId()).isEqualTo(createdRole.getId());
    }

    @Test
    public void testGetRoleByIdNotFound() {
        assertThrows(ServiceException.class, () -> roleService.getById(9999));
    }

    @Test
    public void testGetAllRoles() {
        roleService.create(roleDto);
        List<RoleDto> allRoles = roleService.getAll();
        assertThat(allRoles).isNotEmpty();
    }

    @Test
    public void testUpdateRole() {
        RoleDto createdRole = roleService.create(roleDto);
        createdRole.setRoleName("MANAGER");
        roleService.updateById(createdRole.getId(), createdRole);

        RoleDto updatedRole = roleService.getById(createdRole.getId());
        assertThat(updatedRole.getRoleName()).isEqualTo("MANAGER");
    }

    @Test
    public void testDeleteRole() {
        RoleDto createdRole = roleService.create(roleDto);
        roleService.deleteById(createdRole.getId());

        assertThrows(ServiceException.class, () -> roleService.getById(createdRole.getId()));
    }
}