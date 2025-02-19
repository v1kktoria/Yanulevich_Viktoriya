package senla.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dto.UserDto;
import senla.model.User;
import senla.service.UserService;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;

    private final JwtUtils jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    @Transactional
    public String signUp(UserDto userDto) {
        User user = userService.create(userDto);
        return jwtService.generateToken(user);
    }

    public String signIn(UserDto userDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userDto.getUsername(),
                userDto.getPassword()
        ));

        UserDetails user = userDetailsService.loadUserByUsername(userDto.getUsername());
        return jwtService.generateToken(user);
    }
}
