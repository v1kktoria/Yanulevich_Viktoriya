package senla.util.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import senla.dto.ApplicationDto;
import senla.model.Application;
import senla.model.Property;
import senla.model.User;

@Component
@RequiredArgsConstructor
public class ApplicationMapper {

    private final ModelMapper modelMapper;

    public ApplicationDto toDto(Application application) {
        ApplicationDto applicationDto = modelMapper.map(application, ApplicationDto.class);

        applicationDto.setPropertyId(application.getProperty() != null ? application.getProperty().getId() : null);
        applicationDto.setTenantId(application.getTenant() != null ? application.getTenant().getId() : null);

        return applicationDto;
    }

    public Application toEntity(ApplicationDto applicationDto, Property property, User tenant) {
        Application application = modelMapper.map(applicationDto, Application.class);

        application.setProperty(property);
        application.setTenant(tenant);

        return application;
    }

    public void updateEntity(ApplicationDto applicationDto, Application application) {
        modelMapper.map(applicationDto, application);
    }

}
