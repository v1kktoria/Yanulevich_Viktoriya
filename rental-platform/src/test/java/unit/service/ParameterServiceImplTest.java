package unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import senla.dto.ParameterDto;
import senla.exception.ServiceException;
import senla.model.Parameter;
import senla.repository.ParameterRepository;
import senla.service.impl.ParameterServiceImpl;
import senla.util.mappers.ParameterMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ParameterServiceImplTest {

    @Mock
    private ParameterRepository parameterRepository;

    @Mock
    private ParameterMapper parameterMapper;

    @InjectMocks
    private ParameterServiceImpl parameterService;

    private ParameterDto parameterDto;

    private Parameter parameter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        parameterDto = new ParameterDto();
        parameterDto.setId(1);

        parameter = new Parameter();
        parameter.setId(1);
    }

    @Test
    void testCreate() {
        when(parameterMapper.toEntity(parameterDto)).thenReturn(parameter);
        when(parameterRepository.save(parameter)).thenReturn(parameter);
        when(parameterMapper.toDto(parameter)).thenReturn(parameterDto);

        ParameterDto createdParameter = parameterService.create(parameterDto);

        assertNotNull(createdParameter);
        assertEquals(1, createdParameter.getId());
        verify(parameterRepository, times(1)).save(parameter);
    }

    @Test
    void testGetById() {
        when(parameterRepository.findById(1)).thenReturn(Optional.of(parameter));
        when(parameterMapper.toDto(parameter)).thenReturn(parameterDto);

        ParameterDto result = parameterService.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        when(parameterRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> parameterService.getById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testUpdateById() {
        when(parameterRepository.findById(1)).thenReturn(Optional.of(parameter));

        parameterDto.setId(1);
        parameterService.updateById(1, parameterDto);

        verify(parameterMapper, times(1)).updateEntity(parameterDto, parameter);
        verify(parameterRepository, times(1)).save(parameter);
    }

    @Test
    void testUpdateByIdNotFound() {
        when(parameterRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> parameterService.updateById(1, parameterDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        when(parameterRepository.findById(1)).thenReturn(Optional.of(parameter));

        parameterService.deleteById(1);

        verify(parameterRepository, times(1)).delete(parameter);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(parameterRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> parameterService.deleteById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }
}
