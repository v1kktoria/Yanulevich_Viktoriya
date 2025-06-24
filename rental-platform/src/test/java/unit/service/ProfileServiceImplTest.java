package unit.service;

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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProfileServiceImplTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileMapper profileMapper;

    @InjectMocks
    private ProfileServiceImpl profileService;

    private Integer profileId = 1;
    private Integer userId = 100;
    private Profile profile;
    private ProfileDto profileDto;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(userId);

        profile = new Profile();
        profile.setId(profileId);
        profile.setUser(user);

        profileDto = new ProfileDto();
        profileDto.setId(profileId);
        profileDto.setUserId(userId);
    }

    @Test
    void testCreate() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(profileRepository.existsByUserId(userId)).thenReturn(false);
        when(profileMapper.toEntity(profileDto, user)).thenReturn(profile);
        when(profileRepository.save(profile)).thenReturn(profile);
        when(profileMapper.toDto(profile)).thenReturn(profileDto);

        ProfileDto createdProfile = profileService.create(profileDto);

        assertEquals(profileId, createdProfile.getId());
        verify(profileRepository, times(1)).save(profile);
    }

    @Test
    void testCreateUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> profileService.create(profileDto));

        assertEquals("Объект с ID 100 не найден", exception.getMessage());
    }

    @Test
    void testCreateProfileAlreadyExists() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(profileRepository.existsByUserId(userId)).thenReturn(true);

        ServiceException exception = assertThrows(ServiceException.class, () -> profileService.create(profileDto));

        assertEquals("Профиль для пользователя с id 100 уже существует", exception.getMessage());
    }

    @Test
    void testGetById() {
        when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));
        when(profileMapper.toDto(profile)).thenReturn(profileDto);

        ProfileDto retrievedProfile = profileService.getById(profileId);

        assertEquals(profileId, retrievedProfile.getId());
        verify(profileRepository, times(1)).findById(profileId);
    }

    @Test
    void testGetByIdNotFound() {
        when(profileRepository.findById(profileId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> profileService.getById(profileId));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testGetAll() {
        when(profileRepository.findAll()).thenReturn(List.of(profile));
        when(profileMapper.toDto(profile)).thenReturn(profileDto);

        List<ProfileDto> profiles = profileService.getAll();

        assertNotNull(profiles);
        assertEquals(1, profiles.size());
        verify(profileRepository, times(1)).findAll();
    }

    @Test
    void testUpdateById() {
        when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));
        //when(profileMapper.updateEntity(profileDto, profile)).thenReturn(profile);

        profileService.updateById(profileId, profileDto);

        verify(profileRepository, times(1)).save(profile);
    }

    @Test
    void testUpdateByIdNotFound() {
        when(profileRepository.findById(profileId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> profileService.updateById(profileId, profileDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));

        profileService.deleteById(profileId);

        verify(profileRepository, times(1)).delete(profile);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(profileRepository.findById(profileId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> profileService.deleteById(profileId));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }
}
