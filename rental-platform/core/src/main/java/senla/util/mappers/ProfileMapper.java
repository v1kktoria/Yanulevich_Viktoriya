package senla.util.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import senla.dto.ProfileDto;
import senla.model.Profile;
import senla.model.User;

@Component
@RequiredArgsConstructor
public class ProfileMapper {

    private final ModelMapper modelMapper;

    public ProfileDto toDto(Profile profile) {
        ProfileDto profileDto = modelMapper.map(profile, ProfileDto.class);
        profileDto.setUserId(profile.getUser() != null ? profile.getUser().getId() : null);
        return profileDto;
    }

    public Profile toEntity(ProfileDto profileDto, User user) {
        Profile profile = modelMapper.map(profileDto, Profile.class);
        profile.setUser(user);
        return profile;
    }

    public void updateEntity(ProfileDto profileDto, Profile profile) {
        modelMapper.map(profileDto, profile);
    }
}
