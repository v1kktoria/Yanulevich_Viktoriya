package service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import senla.Main;
import senla.dto.ApplicationDto;
import senla.exception.ServiceException;
import senla.service.ApplicationService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = Main.class)
@RequiredArgsConstructor
@Transactional
@ActiveProfiles("test")
public class ApplicationServiceIntegrationTest {

    @Autowired
    private ApplicationService applicationService;

    private ApplicationDto applicationDto;

    @BeforeEach
    public void setUp() {
        applicationDto = new ApplicationDto();
        applicationDto.setPropertyId(1);
        applicationDto.setTenantId(1);
        applicationDto.setMessage("Message");
    }

    @Test
    public void testCreateApplication() {
        ApplicationDto createdApplication = applicationService.create(applicationDto);
        assertThat(createdApplication).isNotNull();
        assertThat(createdApplication.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetApplicationById() {
        ApplicationDto createdApplication = applicationService.create(applicationDto);
        ApplicationDto fetchedApplication = applicationService.getById(createdApplication.getId());
        assertThat(fetchedApplication).isNotNull();
        assertThat(fetchedApplication.getId()).isEqualTo(createdApplication.getId());
    }

    @Test
    public void testGetApplicationByIdNotFound() {
        assertThrows(ServiceException.class, () -> applicationService.getById(9999));
    }

    @Test
    public void testGetAllApplications() {
        applicationService.create(applicationDto);
        assertThat(applicationService.getAll()).isNotEmpty();
    }

    @Test
    public void testGetApplicationsByPropertyId() {
        applicationService.create(applicationDto);
        assertThat(applicationService.getByPropertyId(1)).isNotEmpty();
    }

    @Test
    public void testUpdateApplication() {
        ApplicationDto createdApplication = applicationService.create(applicationDto);
        createdApplication.setMessage("Updated message");
        applicationService.updateById(createdApplication.getId(), createdApplication);

        ApplicationDto updatedApplication = applicationService.getById(createdApplication.getId());
        assertThat(updatedApplication.getMessage()).isEqualTo("Updated message");
    }

    @Test
    public void testDeleteApplication() {
        ApplicationDto createdApplication = applicationService.create(applicationDto);
        applicationService.deleteById(createdApplication.getId());

        assertThrows(ServiceException.class, () -> applicationService.getById(createdApplication.getId()));
    }
}