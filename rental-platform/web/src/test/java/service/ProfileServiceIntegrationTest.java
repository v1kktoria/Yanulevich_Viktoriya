package service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import senla.Main;
import senla.dto.ProfileDto;
import senla.exception.ServiceException;
import senla.service.ProfileService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = Main.class)
@RequiredArgsConstructor
@Transactional
@ActiveProfiles("test")
public class ProfileServiceIntegrationTest {

    @Autowired
    private ProfileService profileService;

    private ProfileDto profileDto;

    @BeforeEach
    public void setUp() {
        profileDto = new ProfileDto();
        profileDto.setUserId(1);
        profileDto.setFirstname("First Name");
        profileDto.setLastname("Last Name");
        profileDto.setEmail("example@example.com");
    }

    @Test
    public void testCreateProfile() {
        ProfileDto createdProfile = profileService.create(profileDto);
        assertThat(createdProfile).isNotNull();
        assertThat(createdProfile.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetProfileById() {
        ProfileDto createdProfile = profileService.create(profileDto);
        ProfileDto fetchedProfile = profileService.getById(createdProfile.getId());
        assertThat(fetchedProfile).isNotNull();
        assertThat(fetchedProfile.getId()).isEqualTo(createdProfile.getId());
    }

    @Test
    public void testGetProfileByIdNotFound() {
        assertThrows(ServiceException.class, () -> profileService.getById(9999));
    }

    @Test
    public void testGetAllProfiles() {
        profileService.create(profileDto);
        assertThat(profileService.getAll()).isNotEmpty();
    }

    @Test
    public void testUpdateProfile() {
        ProfileDto createdProfile = profileService.create(profileDto);
        createdProfile.setFirstname("Updated Name");
        profileService.updateById(createdProfile.getId(), createdProfile);

        ProfileDto updatedProfile = profileService.getById(createdProfile.getId());
        assertThat(updatedProfile.getFirstname()).isEqualTo("Updated Name");
    }

    @Test
    public void testDeleteProfile() {
        ProfileDto createdProfile = profileService.create(profileDto);
        profileService.deleteById(createdProfile.getId());

        assertThrows(ServiceException.class, () -> profileService.getById(createdProfile.getId()));
    }
}