package unit.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import senla.controller.AuthController;
import senla.dto.UserDto;
import senla.security.AuthenticationService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthController authController;

    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("testPassword");
    }

    @Test
    void testSignUp() {
        when(authenticationService.signUp(userDto)).thenReturn(userDto);

        ResponseEntity<UserDto> response = authController.signUp(userDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("testUser", response.getBody().getUsername());
    }

    @Test
    void testSignIn() {
        String token = "JwtToken";
        when(authenticationService.signIn(userDto)).thenReturn(token);

        String response = authController.signIn(userDto);

        assertNotNull(response);
        assertEquals(token, response);
    }
}
