package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.aop.MeasureExecutionTime;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.User;
import senla.repository.UserRepository;
import senla.service.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public void create(Authentication authentication) {

        OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        String keycloakId = (String) principal.getAttribute("sub");
        String username = (String) principal.getAttribute("preferred_username");

        if (userRepository.existsByKeycloakId(keycloakId)) {
            throw new ServiceException(ServiceExceptionEnum.USER_ALREADY_EXISTS, username);
        }

        User user = new User();
        user.setKeycloakId(keycloakId);
        user.setUsername(username);

        userRepository.save(user);
        log.info("Пользователь с ID: {} успешно создан", user.getId());
    }

    @Override
    public User getByKeycloakId(String id) {
        User user = userRepository.findByKeycloakId(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        log.info("Пользователь с ID: {} успешно получен", id);
        return user;
    }

}
