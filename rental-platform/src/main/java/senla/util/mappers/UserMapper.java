package senla.util.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import senla.dto.UserDto;
import senla.model.User;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper{

    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "profileId", source = "profile.id")
    @Mapping(target = "favoriteId", source = "favorite.id")
    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    void updateEntity(UserDto userDto,@MappingTarget User user);
}
