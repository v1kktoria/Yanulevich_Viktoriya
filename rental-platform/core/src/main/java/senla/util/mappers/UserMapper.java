package senla.util.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import senla.dto.ApplicationDto;
import senla.dto.PropertyDto;
import senla.dto.ReviewDto;
import senla.dto.RoleDto;
import senla.dto.UserDto;
import senla.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserDto toDto(User user) {

        UserDto userDto = modelMapper.map(user, UserDto.class);

        userDto.setRoles(user.getRoles() != null ? user.getRoles().stream()
                .map(role -> modelMapper.map(role, RoleDto.class))
                .collect(Collectors.toList()) : new ArrayList<>());

        userDto.setProfileId(user.getProfile() != null ? user.getProfile().getId() : null);
        userDto.setFavoriteId(user.getFavorite() != null ? user.getFavorite().getId() : null);

        return userDto;
    }

    public User toEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public void updateEntity(UserDto userDto, User user) {
        modelMapper.map(userDto, user);
    }
}
