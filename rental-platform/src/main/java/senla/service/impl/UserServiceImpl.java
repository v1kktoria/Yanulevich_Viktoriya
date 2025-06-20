package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.aop.MeasureExecutionTime;
import senla.dto.UserDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Role;
import senla.model.User;
import senla.repository.RoleRepository;
import senla.repository.UserRepository;
import senla.service.UserService;
import senla.util.mappers.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public UserDto create(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setPassword((passwordEncoder.encode(userDto.getPassword())));

        Role userRole = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND_WITH_NAME, "USER"));

        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);
        userRole.getUsers().add(user);

        User savedUser;

        try {
            savedUser = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException(ServiceExceptionEnum.USER_ALREADY_EXISTS, userDto.getUsername());
        }

        log.info("Пользователь с ID: {} успешно создан", savedUser.getId());
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto getById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        log.info("Пользователь с ID: {} успешно получен", id);
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        log.info("Найдено {} пользователей", users.size());
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateById(Integer id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        userDto.setId(id);
        userMapper.updateEntity(userDto, user);

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException(ServiceExceptionEnum.USER_ALREADY_EXISTS, userDto.getUsername());
        }

        log.info("Пользователь с ID: {} успешно обновлен", id);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        userRepository.delete(user);
        log.info("Пользователь с ID: {} успешно удален", id);
    }

}
