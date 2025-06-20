package senla.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import senla.security.AuthenticationService;
import senla.dto.UserDto;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(@RequestBody @Valid UserDto userDto) {
        log.info("Запрос на регистрацию нового пользователя с именем: {}", userDto.getUsername());
        UserDto user = authenticationService.signUp(userDto);
        log.info("Пользователь с именем: {} успешно зарегистрирован", user.getUsername());
        return ResponseEntity.status(201).body(user);
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestBody @Valid UserDto userDto) {
        log.info("Запрос на авторизацию пользователя с именем: {}", userDto.getUsername());
        String result = authenticationService.signIn(userDto);
        log.info("Пользователь с именем: {} успешно авторизован", userDto.getUsername());
        return result;
    }
}
