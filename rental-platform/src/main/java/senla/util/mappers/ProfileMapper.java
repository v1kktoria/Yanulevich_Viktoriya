package senla.util.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import senla.dto.ProfileDto;
import senla.model.Profile;
import senla.model.User;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(target = "userId", source = "user.id")
    ProfileDto toDto(Profile profile);

    @Mapping(target = "user", expression = "java(user)")
    Profile toEntity(ProfileDto profileDto, User user);

    @Mapping(target = "user", ignore = true)
    void updateEntity(ProfileDto profileDto,@MappingTarget Profile profile);
}
