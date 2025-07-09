package senla.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import senla.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    @GetMapping("/init")
    public ResponseEntity<String> initialize(Authentication authentication) {
        log.info("Инициализация пользователя после логина через Keycloak");
        userService.create(authentication);
        return ResponseEntity.ok("Пользователь успешно зарегистрирован");
    }
}
