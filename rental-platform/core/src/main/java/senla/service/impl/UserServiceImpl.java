package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dao.UserDao;
import senla.dto.UserDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.User;
import senla.service.UserService;
import senla.util.mappers.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@MeasureExecutionTime
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final UserMapper userMapper;

    @Override
    public UserDto create(UserDto userDto) {
        if (userDao.existsByUsername(userDto.getUsername())) {
            throw new ServiceException(ServiceExceptionEnum.USER_ALREADY_EXISTS, userDto.getUsername());
        }
        User user = userMapper.toEntity(userDto);
        User savedUser = userDao.save(user);
        log.info("Пользователь с ID: {} успешно создан", savedUser.getId());
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto getById(Integer id) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        log.info("Пользователь с ID: {} успешно получен", id);
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userDao.findAll();
        log.info("Найдено {} пользователей", users.size());
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateById(Integer id, UserDto userDto) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        if (!userDto.getUsername().equals(user.getUsername()) && userDao.existsByUsername(userDto.getUsername())) {
            throw new ServiceException(ServiceExceptionEnum.USER_ALREADY_EXISTS, userDto.getUsername());
        }

        userDto.setId(id);
        userMapper.updateEntity(userDto, user);
        userDao.update(user);
        log.info("Пользователь с ID: {} успешно обновлен", id);
    }

    @Override
    public void deleteById(Integer id) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        userDao.delete(user);
        log.info("Пользователь с ID: {} успешно удален", id);
    }

    @Override
    public List<UserDto> getAllWithEssentialDetails() {
        List<User> users = userDao.findAllWithEssentialDetails();
        log.info("Найдено {} пользователей с основными деталями", users.size());
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}
