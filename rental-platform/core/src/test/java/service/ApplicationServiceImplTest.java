package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import senla.dto.ApplicationDto;
import senla.exception.ServiceException;
import senla.model.Application;
import senla.model.Profile;
import senla.model.Property;
import senla.model.User;
import senla.model.constant.Status;
import senla.repository.ApplicationRepository;
import senla.repository.PropertyRepository;
import senla.repository.UserRepository;
import senla.service.ChatService;
import senla.service.NotificationService;
import senla.service.impl.ApplicationServiceImpl;
import senla.util.mappers.ApplicationMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ApplicationServiceImplTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApplicationMapper applicationMapper;

    @Mock
    private ChatService chatService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    private ApplicationDto applicationDto;

    private Property property;

    private User tenant;

    private User owner;

    private Application application;

    private Profile profile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        applicationDto = new ApplicationDto();
        applicationDto.setId(1);
        applicationDto.setPropertyId(1);
        applicationDto.setTenantId(1);
        applicationDto.setOwnerId(2);

        property = new Property();
        property.setId(1);

        profile = new Profile();
        profile.setEmail("test@example.com");

        tenant = new User();
        tenant.setId(1);
        tenant.setUsername("Tenant");

        owner = new User();
        owner.setId(2);
        owner.setUsername("Owner");
        owner.setProfile(profile);

        application = new Application();
        application.setId(1);
        application.setTenant(tenant);
        application.setOwner(owner);
        application.setStatus(Status.PENDING);
        application.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testCreate() {
        when(propertyRepository.findById(1)).thenReturn(Optional.of(property));
        when(userRepository.findById(1)).thenReturn(Optional.of(tenant));
        when(userRepository.findById(2)).thenReturn(Optional.of(owner));
        when(applicationMapper.toEntity(applicationDto, property, tenant)).thenReturn(application);
        when(applicationRepository.save(application)).thenReturn(application);
        when(applicationMapper.toDto(application)).thenReturn(applicationDto);

        ApplicationDto createdApplication = applicationService.create(applicationDto);

        assertNotNull(createdApplication);
        assertEquals(1, createdApplication.getId());
        verify(applicationRepository, times(1)).save(application);
        verify(notificationService, times(1)).sendNewApplicationNotification(owner.getProfile().getEmail(), tenant.getUsername(), property.getDescription());
    }

    @Test
    void testCreatePropertyNotFound() {
        when(propertyRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> applicationService.create(applicationDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
        verify(applicationRepository, never()).save(any());
    }

    @Test
    void testAcceptApplication() {
        when(applicationRepository.findById(1)).thenReturn(Optional.of(application));

        applicationService.acceptApplication(1);

        assertEquals(Status.APPROVED, application.getStatus());
        verify(applicationRepository, times(1)).save(application);
        verify(chatService, times(1)).create(tenant.getId(), owner.getId());
        verify(notificationService, times(1)).sendApprovalNotification(application);
    }

    @Test
    void testAcceptApplicationAlreadyApproved() {
        application.setStatus(Status.APPROVED);
        when(applicationRepository.findById(1)).thenReturn(Optional.of(application));

        applicationService.acceptApplication(1);

        assertEquals(Status.APPROVED, application.getStatus());
        verify(applicationRepository, never()).save(application);
        verify(chatService, never()).create(any(), any());
        verify(notificationService, never()).sendApprovalNotification(any());
    }

    @Test
    void testRejectApplication() {
        when(applicationRepository.findById(1)).thenReturn(Optional.of(application));

        applicationService.rejectApplication(1);

        assertEquals(Status.REJECTED, application.getStatus());
        verify(applicationRepository, times(1)).save(application);
        verify(notificationService, times(1)).sendRejectionNotification(application);
    }

    @Test
    void testRejectApplicationAlreadyRejected() {
        application.setStatus(Status.REJECTED);
        when(applicationRepository.findById(1)).thenReturn(Optional.of(application));

        applicationService.rejectApplication(1);

        assertEquals(Status.REJECTED, application.getStatus());
        verify(applicationRepository, never()).save(application);
        verify(notificationService, never()).sendRejectionNotification(any());
    }

    @Test
    void testDeleteById() {
        when(applicationRepository.findById(1)).thenReturn(Optional.of(application));

        applicationService.deleteById(1);

        verify(applicationRepository, times(1)).delete(application);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(applicationRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> applicationService.deleteById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }
}
