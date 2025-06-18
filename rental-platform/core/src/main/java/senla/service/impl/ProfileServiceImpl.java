package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dto.ProfileDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Profile;
import senla.model.User;
import senla.repository.ProfileRepository;
import senla.repository.UserRepository;
import senla.service.ProfileService;
import senla.util.mappers.ProfileMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    private final UserRepository userRepository;

    private final ProfileMapper profileMapper;

    @Transactional
    @Override
    public ProfileDto create(ProfileDto profileDto) {
        User user = userRepository.findById(profileDto.getUserId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, profileDto.getUserId()));

        if (profileRepository.existsByUserId(profileDto.getUserId())) {
            throw new ServiceException(ServiceExceptionEnum.PROFILE_ALREADY_EXISTS, profileDto.getUserId());
        }

        Profile profile = profileMapper.toEntity(profileDto, user);
        ProfileDto createdProfile = profileMapper.toDto(profileRepository.save(profile));
        log.info("Профиль успешно добавлен с ID: {}", createdProfile.getId());
        return createdProfile;
    }

    @Override
    public ProfileDto getById(Integer id) {
        ProfileDto profile = profileMapper.toDto(profileRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id)));
        log.info("Профиль успешно получен с ID: {}", profile.getId());
        return profile;
    }

    @Override
    public List<ProfileDto> getAll() {
        List<Profile> profiles = profileRepository.findAll();
        List<ProfileDto> profileDtos = profiles.stream()
                .map(profileMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} профилей", profileDtos.size());
        return profileDtos;
    }

    @Transactional
    @Override
    public void updateById(Integer id, ProfileDto profileDto) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        profileDto.setId(id);
        profileMapper.updateEntity(profileDto, profile);
        profileRepository.save(profile);
        log.info("Профиль с ID: {} успешно обновлен", id);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        profileRepository.delete(profile);
        log.info("Профиль с ID: {} успешно удален", id);
    }
}