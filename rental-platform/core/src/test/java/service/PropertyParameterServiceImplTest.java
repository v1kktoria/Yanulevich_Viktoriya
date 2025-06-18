package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import senla.dto.ParameterDto;
import senla.dto.PropertyDto;
import senla.dto.PropertyParameterDto;
import senla.exception.ServiceException;
import senla.model.PropertyParameter;
import senla.model.id.PropertyParameterId;
import senla.repository.PropertyParameterRepository;
import senla.service.impl.PropertyParameterServiceImpl;
import senla.util.mappers.PropertyParameterMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PropertyParameterServiceImplTest {

    @Mock
    private PropertyParameterRepository propertyParameterRepository;

    @Mock
    private PropertyParameterMapper propertyParameterMapper;

    @InjectMocks
    private PropertyParameterServiceImpl propertyParameterService;

    private PropertyParameterDto propertyParameterDto;

    private PropertyParameter propertyParameter;

    private PropertyDto propertyDto;

    private ParameterDto parameterDto;

    private PropertyParameterId propertyParameterId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        propertyDto = new PropertyDto();
        propertyDto.setId(1);

        parameterDto = new ParameterDto();
        parameterDto.setId(1);

        propertyParameterId = new PropertyParameterId(1, 1);

        propertyParameter = new PropertyParameter();
        propertyParameter.setId(propertyParameterId);

        propertyParameterDto = new PropertyParameterDto();
        propertyParameterDto.setId(propertyParameterId);
    }

    @Test
    void testCreate() {
        when(propertyParameterMapper.toEntity(propertyParameterDto)).thenReturn(propertyParameter);
        when(propertyParameterRepository.save(propertyParameter)).thenReturn(propertyParameter);

        propertyParameterService.create(propertyParameterDto);

        verify(propertyParameterRepository, times(1)).save(propertyParameter);
    }

    @Test
    void testGetByPropertyAndParameter() {
        when(propertyParameterRepository.findById(propertyParameterId)).thenReturn(Optional.of(propertyParameter));
        when(propertyParameterMapper.toDto(propertyParameter)).thenReturn(propertyParameterDto);

        PropertyParameterDto result = propertyParameterService.getByPropertyAndParameter(propertyDto, parameterDto);

        assertNotNull(result);
        assertEquals(propertyParameterDto.getId(), result.getId());
        verify(propertyParameterRepository, times(1)).findById(propertyParameterId);
    }

    @Test
    void testGetByPropertyAndParameterNotFound() {
        when(propertyParameterRepository.findById(propertyParameterId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            propertyParameterService.getByPropertyAndParameter(propertyDto, parameterDto);
        });

        assertEquals("Объект с ID PropertyParameterId(property_id=1, parameter_id=1) не найден", exception.getMessage());
    }

    @Test
    void testGetAll() {
        when(propertyParameterRepository.findAll()).thenReturn(List.of(propertyParameter));
        when(propertyParameterMapper.toDto(propertyParameter)).thenReturn(propertyParameterDto);

        var result = propertyParameterService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(propertyParameterRepository, times(1)).findAll();
    }

    @Test
    void testDeleteByPropertyAndParameter() {
        when(propertyParameterRepository.findById(propertyParameterId)).thenReturn(Optional.of(propertyParameter));

        propertyParameterService.deleteByPropertyAndParameter(propertyDto, parameterDto);

        verify(propertyParameterRepository, times(1)).delete(propertyParameter);
    }

    @Test
    void testDeleteByPropertyAndParameterNotFound() {
        when(propertyParameterRepository.findById(propertyParameterId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            propertyParameterService.deleteByPropertyAndParameter(propertyDto, parameterDto);
        });

        assertEquals("Объект с ID PropertyParameterId(property_id=1, parameter_id=1) не найден", exception.getMessage());
    }
}
