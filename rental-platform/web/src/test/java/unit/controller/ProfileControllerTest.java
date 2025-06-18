package unit.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import senla.controller.ProfileController;
import senla.dto.ProfileDto;
import senla.service.ProfileService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProfileControllerTest {

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private ProfileController profileController;

    private ProfileDto profileDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        profileDto = new ProfileDto();
        profileDto.setId(1);
        profileDto.setUserId(1);
        profileDto.setLastname("Test User");
        profileDto.setEmail("test@example.com");
    }

    @Test
    void testCreateProfile() {
        when(profileService.create(profileDto)).thenReturn(profileDto);

        ResponseEntity<ProfileDto> response = profileController.createProfile(profileDto);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Test User", response.getBody().getLastname());
    }

    @Test
    void testGetAllProfiles() {
        when(profileService.getAll()).thenReturn(List.of(profileDto));

        ResponseEntity<List<ProfileDto>> response = profileController.getAllProfiles();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Test User", response.getBody().get(0).getLastname());
    }

    @Test
    void testGetProfile() {
        when(profileService.getById(1)).thenReturn(profileDto);

        ResponseEntity<ProfileDto> response = profileController.getProfile(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test User", response.getBody().getLastname());
    }

    @Test
    void testUpdateProfile() {
        doNothing().when(profileService).updateById(1, profileDto);

        ResponseEntity<String> response = profileController.updateProfile(1, profileDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Профиль успешно обновлен", response.getBody());
    }

    @Test
    void testDeleteProfile() {
        doNothing().when(profileService).deleteById(1);

        ResponseEntity<String> response = profileController.deleteProfile(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Профиль успешно удален", response.getBody());
    }
}
