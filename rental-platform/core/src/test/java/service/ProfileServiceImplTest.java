package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import senla.dto.ProfileDto;
import senla.exception.ServiceException;
import senla.model.Profile;
import senla.model.User;
import senla.repository.ProfileRepository;
import senla.repository.UserRepository;
import senla.service.impl.ProfileServiceImpl;
import senla.util.mappers.ProfileMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProfileServiceImplTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileMapper profileMapper;

    @InjectMocks
    private ProfileServiceImpl profileService;

    private ProfileDto profileDto;

    private Profile profile;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        profileDto = new ProfileDto();
        profileDto.setId(1);
        profileDto.setUserId(1);

        user = new User();
        user.setId(1);

        profile = new Profile();
        profile.setId(1);
        profile.setUser(user);
        profile.setRegistrationDate(LocalDateTime.now());
    }

    @Test
    void testCreate() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(profileMapper.toEntity(profileDto, user)).thenReturn(profile);
        when(profileRepository.save(profile)).thenReturn(profile);
        when(profileMapper.toDto(profile)).thenReturn(profileDto);

        ProfileDto createdProfile = profileService.create(profileDto);

        assertNotNull(createdProfile);
        assertEquals(1, createdProfile.getId());
        verify(profileRepository, times(1)).save(profile);
    }

    @Test
    void testCreateUserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> profileService.create(profileDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testGetById() {
        when(profileRepository.findById(1)).thenReturn(Optional.of(profile));
        when(profileMapper.toDto(profile)).thenReturn(profileDto);

        ProfileDto result = profileService.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        when(profileRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> profileService.getById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testUpdateById() {
        when(profileRepository.findById(1)).thenReturn(Optional.of(profile));

        profileDto.setId(1);
        profileService.updateById(1, profileDto);

        verify(profileMapper, times(1)).updateEntity(profileDto, profile);
        verify(profileRepository, times(1)).save(profile);
    }

    @Test
    void testUpdateByIdNotFound() {
        when(profileRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> profileService.updateById(1, profileDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        when(profileRepository.findById(1)).thenReturn(Optional.of(profile));

        profileService.deleteById(1);

        verify(profileRepository, times(1)).delete(profile);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(profileRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> profileService.deleteById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }
}
