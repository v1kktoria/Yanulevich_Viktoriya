package unit.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import senla.controller.UserController;
import senla.dto.UserDto;
import senla.service.UserService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userDto = new UserDto();
        userDto.setId(1);
        userDto.setUsername("testUser");
    }

    @Test
    void testGetAllUsers() {
        when(userService.getAll()).thenReturn(List.of(userDto));

        ResponseEntity<List<UserDto>> response = userController.getAllUsers();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("testUser", response.getBody().get(0).getUsername());
    }

    @Test
    void testGetUser() {
        when(userService.getById(1)).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.getUser(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("testUser", response.getBody().getUsername());
    }

    @Test
    void testUpdateUser() {
        doNothing().when(userService).updateById(1, userDto);

        ResponseEntity<String> response = userController.updateUser(1, userDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Пользователь успешно обновлен", response.getBody());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userService).deleteById(1);

        ResponseEntity<String> response = userController.deleteUser(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Пользователь успешно удален", response.getBody());
    }
}
