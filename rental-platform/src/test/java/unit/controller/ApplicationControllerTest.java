package unit.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import senla.controller.ApplicationController;
import senla.dto.ApplicationDto;
import senla.service.ApplicationService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationControllerTest {

    @Mock
    private ApplicationService applicationService;

    @InjectMocks
    private ApplicationController applicationController;

    private ApplicationDto applicationDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        applicationDto = new ApplicationDto();
        applicationDto.setId(1);
        applicationDto.setMessage("Test Application");
    }

    @Test
    void testCreateApplication() {
        when(applicationService.create(applicationDto)).thenReturn(applicationDto);

        ResponseEntity<ApplicationDto> response = applicationController.createApplication(applicationDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Test Application", response.getBody().getMessage());
    }

    @Test
    void testGetAllApplications() {
        when(applicationService.getAll()).thenReturn(List.of(applicationDto));

        ResponseEntity<List<ApplicationDto>> response = applicationController.getAllApplications();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetApplication() {
        when(applicationService.getById(1)).thenReturn(applicationDto);

        ResponseEntity<ApplicationDto> response = applicationController.getApplication(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Application", response.getBody().getMessage());
    }

    @Test
    void testDeleteApplication() {
        doNothing().when(applicationService).deleteById(1);

        ResponseEntity<String> response = applicationController.deleteApplication(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Заявка успешно удалена", response.getBody());
    }

    @Test
    void testAcceptApplication() {
        doNothing().when(applicationService).acceptApplication(1);

        ResponseEntity<String> response = applicationController.acceptApplication(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Заявка принята", response.getBody());
    }

    @Test
    void testRejectApplication() {
        doNothing().when(applicationService).rejectApplication(1);

        ResponseEntity<String> response = applicationController.rejectApplication(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Заявка отклонена", response.getBody());
    }
}
