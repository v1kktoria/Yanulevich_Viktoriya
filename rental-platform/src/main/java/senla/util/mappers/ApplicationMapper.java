package senla.util.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import senla.dto.ApplicationDto;
import senla.model.Application;
import senla.model.Property;
import senla.model.User;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Mapping(target = "propertyId", source = "property.id")
    @Mapping(target = "tenantId", source = "tenant.id")
    ApplicationDto toDto(Application application);

    @Mapping(target = "property", expression = "java(property)")
    @Mapping(target = "tenant", expression = "java(tenant)")
    @Mapping(target = "createdAt",  ignore = true)
    Application toEntity(ApplicationDto applicationDto, Property property, User tenant);

    @Mapping(target = "property", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    void updateEntity(ApplicationDto applicationDto,@MappingTarget Application application);

}
