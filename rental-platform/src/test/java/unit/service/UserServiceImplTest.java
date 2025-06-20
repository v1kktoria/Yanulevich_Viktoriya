package unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import senla.dto.UserDto;
import senla.exception.ServiceException;
import senla.model.Role;
import senla.model.User;
import senla.repository.RoleRepository;
import senla.repository.UserRepository;
import senla.service.impl.UserServiceImpl;
import senla.util.mappers.UserMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;
    private Role role;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        role = new Role();
        role.setRoleName("USER");

        user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setPassword("encodedPassword");
        user.setRoles(List.of(role));

        userDto = new UserDto();
        userDto.setId(1);
        userDto.setUsername("testuser");
        userDto.setPassword("password");
    }

    @Test
    void testCreate() {
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByRoleName("USER")).thenReturn(Optional.of(role));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto createdUser = userService.create(userDto);

        assertEquals(userDto.getUsername(), createdUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testCreateUserAlreadyExists() {
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByRoleName("USER")).thenReturn(Optional.of(role));
        when(userRepository.save(user)).thenThrow(new org.springframework.dao.DataIntegrityViolationException(""));

        ServiceException exception = assertThrows(ServiceException.class, () -> userService.create(userDto));

        assertEquals("Пользователь с именем testuser уже существует", exception.getMessage());
    }

    @Test
    void testGetById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto retrievedUser = userService.getById(1);

        assertEquals(userDto.getId(), retrievedUser.getId());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testGetByIdNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> userService.getById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testGetAll() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        List<UserDto> users = userService.getAll();

        assertNotNull(users);
        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testUpdateById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.updateById(1, userDto);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateByIdNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> userService.updateById(1, userDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        userService.deleteById(1);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> userService.deleteById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }
}
