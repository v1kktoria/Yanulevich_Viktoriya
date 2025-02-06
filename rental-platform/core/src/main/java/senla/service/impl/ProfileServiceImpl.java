package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dao.ProfileDao;
import senla.dao.UserDao;
import senla.dto.ProfileDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Profile;
import senla.model.User;
import senla.service.ProfileService;
import senla.util.mappers.ProfileMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@MeasureExecutionTime
public class ProfileServiceImpl implements ProfileService {

    private final ProfileDao profileDao;

    private final UserDao userDao;

    private final ProfileMapper profileMapper;

    @Override
    public ProfileDto create(ProfileDto profileDto) {
        User user = userDao.findById(profileDto.getUserId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, profileDto.getUserId()));

        Profile profile = profileMapper.toEntity(profileDto, user);
        profile.setRegistrationDate(LocalDateTime.now());
        ProfileDto createdProfile = profileMapper.toDto(profileDao.save(profile));
        log.info("Профиль успешно добавлен с ID: {}", createdProfile.getId());
        return createdProfile;
    }

    @Override
    public ProfileDto getById(Integer id) {
        ProfileDto profile = profileMapper.toDto(profileDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id)));
        log.info("Профиль успешно получен с ID: {}", profile.getId());
        return profile;
    }

    @Override
    public List<ProfileDto> getAll() {
        List<Profile> profiles = profileDao.findAll();
        List<ProfileDto> profileDtos = profiles.stream()
                .map(profileMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} профилей", profileDtos.size());
        return profileDtos;
    }

    @Override
    public void updateById(Integer id, ProfileDto profileDto) {
        Profile profile = profileDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        profileDto.setId(id);
        profileMapper.updateEntity(profileDto, profile);
        profileDao.update(profile);
        log.info("Профиль с ID: {} успешно обновлен", id);
    }

    @Override
    public void deleteById(Integer id) {
        Profile profile = profileDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        profileDao.delete(profile);
        log.info("Профиль с ID: {} успешно удален", id);
    }
}