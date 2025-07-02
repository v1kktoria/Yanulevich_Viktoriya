package senla.util.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import senla.dto.RoleDto;
import senla.model.Role;


@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto toDto(Role role);

    Role toEntity(RoleDto roleDto);

    void updateEntity(RoleDto roleDto,@MappingTarget Role role);
}
