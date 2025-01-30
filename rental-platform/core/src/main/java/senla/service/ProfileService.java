package senla.service;

import senla.dto.ProfileDto;

import java.util.List;

public interface ProfileService {

    ProfileDto create(ProfileDto profileDto);

    ProfileDto getById(Integer id);

    List<ProfileDto> getAll();

    void updateById(Integer id, ProfileDto profileDto);

    void deleteById(Integer id);
}
