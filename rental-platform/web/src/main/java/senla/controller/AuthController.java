package senla.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import senla.security.AuthenticationService;
import senla.dto.UserDto;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public String signUp(@RequestBody @Valid UserDto userDto) {
        return authenticationService.signUp(userDto);
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestBody @Valid UserDto userDto) {
        return authenticationService.signIn(userDto);
    }
}
