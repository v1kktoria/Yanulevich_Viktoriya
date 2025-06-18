package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import senla.dto.UserDto;
import senla.exception.ServiceException;
import senla.model.Role;
import senla.model.User;
import senla.repository.RoleRepository;
import senla.repository.UserRepository;
import senla.service.impl.UserServiceImpl;
import senla.util.mappers.UserMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    private UserDto userDto;

    private User user;

    private Role role;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userDto = new UserDto();
        userDto.setId(1);
        userDto.setUsername("testUser");
        userDto.setPassword("password");

        user = new User();
        user.setId(1);
        user.setUsername("testUser");
        user.setPassword("encodedPassword");

        role = new Role();
        role.setRoleName("USER");
        role.setUsers(new HashSet<>(Set.of(user)));
    }

    @Test
    void testCreate() {
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByRoleName("USER")).thenReturn(Optional.of(role));
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.create(userDto);

        assertNotNull(createdUser);
        assertEquals("testUser", createdUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testCreateUserAlreadyExists() {
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByRoleName("USER")).thenReturn(Optional.of(role));
        when(userRepository.save(user)).thenThrow(DataIntegrityViolationException.class);

        ServiceException exception = assertThrows(ServiceException.class, () -> userService.create(userDto));

        assertEquals("Пользователь с именем testUser уже существует", exception.getMessage());
    }

    @Test
    void testGetById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.getById(1);

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
    }

    @Test
    void testGetByIdNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> userService.getById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testGetAll() {
        List<User> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDto(user)).thenReturn(userDto);

        List<UserDto> result = userService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        userDto.setId(1);
        userService.updateById(1, userDto);

        verify(userMapper, times(1)).updateEntity(userDto, user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateByIdNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> userService.updateById(1, userDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testUpdateUserAlreadyExists() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenThrow(DataIntegrityViolationException.class);

        ServiceException exception = assertThrows(ServiceException.class, () -> userService.updateById(1, userDto));

        assertEquals("Пользователь с именем testUser уже существует", exception.getMessage());
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
