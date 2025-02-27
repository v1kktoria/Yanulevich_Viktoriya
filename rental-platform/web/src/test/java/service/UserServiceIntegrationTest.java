package service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import senla.Main;
import senla.dto.UserDto;
import senla.exception.ServiceException;
import senla.model.User;
import senla.service.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = Main.class)
@RequiredArgsConstructor
@Transactional
@ActiveProfiles("test")
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        userDto = new UserDto();
        userDto.setUsername("user");
        userDto.setPassword("password");
    }

    @Test
    public void testCreateUser() {
        User createdUser = userService.create(userDto);
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserWithExistingUsername() {
        userService.create(userDto);
        assertThrows(ServiceException.class, () -> userService.create(userDto));
    }

    @Test
    public void testGetUserById() {
        User createdUser = userService.create(userDto);
        UserDto fetchedUser = userService.getById(createdUser.getId());
        assertThat(fetchedUser).isNotNull();
        assertThat(fetchedUser.getId()).isEqualTo(createdUser.getId());
    }

    @Test
    public void testGetUserByIdNotFound() {
        assertThrows(ServiceException.class, () -> userService.getById(9999));
    }

    @Test
    public void testGetAllUsers() {
        userService.create(userDto);
        List<UserDto> allUsers = userService.getAll();
        assertThat(allUsers).isNotEmpty();
    }

    @Test
    public void testUpdateUser() {
        User user = userService.create(userDto);
        UserDto createdUser = userService.getById(user.getId());
        createdUser.setUsername("updatedUser");
        userService.updateById(createdUser.getId(), createdUser);

        UserDto updatedUser = userService.getById(createdUser.getId());
        assertThat(updatedUser.getUsername()).isEqualTo("updatedUser");
    }

    @Test
    public void testDeleteUser() {
        User createdUser = userService.create(userDto);
        userService.deleteById(createdUser.getId());

        assertThrows(ServiceException.class, () -> userService.getById(createdUser.getId()));
    }
}