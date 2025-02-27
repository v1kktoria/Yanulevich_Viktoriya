package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import senla.dto.ApplicationDto;
import senla.exception.ServiceException;
import senla.model.Application;
import senla.model.Property;
import senla.model.User;
import senla.repository.ApplicationRepository;
import senla.repository.PropertyRepository;
import senla.repository.UserRepository;
import senla.service.impl.ApplicationServiceImpl;
import senla.util.mappers.ApplicationMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ApplicationServiceImplTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApplicationMapper applicationMapper;

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    private ApplicationDto applicationDto;

    private Property property;

    private User tenant;

    private Application application;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        applicationDto = new ApplicationDto();
        applicationDto.setId(1);
        applicationDto.setPropertyId(1);
        applicationDto.setTenantId(1);

        property = new Property();
        property.setId(1);

        tenant = new User();
        tenant.setId(1);

        application = new Application();
        application.setId(1);
    }

    @Test
    void testCreate() {
        when(propertyRepository.findById(1)).thenReturn(Optional.of(property));
        when(userRepository.findById(1)).thenReturn(Optional.of(tenant));
        when(applicationMapper.toEntity(applicationDto, property, tenant)).thenReturn(application);
        when(applicationRepository.save(application)).thenReturn(application);
        when(applicationMapper.toDto(application)).thenReturn(applicationDto);

        ApplicationDto createdApplication = applicationService.create(applicationDto);

        assertNotNull(createdApplication);
        assertEquals(1, createdApplication.getId());
        verify(applicationRepository, times(1)).save(application);
    }

    @Test
    void testCreatePropertyNotFound() {
        when(propertyRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> applicationService.create(applicationDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
        verify(applicationRepository, never()).save(any());
    }

    @Test
    void testCreateTenantNotFound() {
        when(propertyRepository.findById(1)).thenReturn(Optional.of(property));
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> applicationService.create(applicationDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
        verify(applicationRepository, never()).save(any());
    }

    @Test
    void testGetById() {
        when(applicationRepository.findById(1)).thenReturn(Optional.of(application));
        when(applicationMapper.toDto(application)).thenReturn(applicationDto);

        ApplicationDto result = applicationService.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        when(applicationRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> applicationService.getById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testUpdateById() {
        when(applicationRepository.findById(1)).thenReturn(Optional.of(application));

        applicationService.updateById(1, applicationDto);

        verify(applicationMapper, times(1)).updateEntity(applicationDto, application);
        verify(applicationRepository, times(1)).save(application);
    }

    @Test
    void testUpdateByIdNotFound() {
        when(applicationRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> applicationService.updateById(1, applicationDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
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
