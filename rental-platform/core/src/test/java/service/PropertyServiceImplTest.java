package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import senla.dto.PropertyDto;
import senla.exception.ServiceException;
import senla.model.Property;
import senla.model.User;
import senla.repository.PropertyRepository;
import senla.service.impl.PropertyServiceImpl;
import senla.util.mappers.PropertyMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PropertyServiceImplTest {

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private PropertyMapper propertyMapper;

    @InjectMocks
    private PropertyServiceImpl propertyService;

    private PropertyDto propertyDto;

    private Property property;

    private User owner;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        owner = new User();
        owner.setId(1);

        propertyDto = new PropertyDto();
        propertyDto.setId(1);

        property = new Property();
        property.setId(1);
        property.setOwner(owner);
    }

    @Test
    void testCreate() {
        when(propertyMapper.toEntity(propertyDto)).thenReturn(property);
        when(propertyRepository.save(property)).thenReturn(property);
        when(propertyMapper.toDto(property)).thenReturn(propertyDto);

        PropertyDto createdProperty = propertyService.create(propertyDto);

        assertNotNull(createdProperty);
        assertEquals(1, createdProperty.getId());
        verify(propertyRepository, times(1)).save(property);
    }

    @Test
    void testGetById() {
        when(propertyRepository.findById(1)).thenReturn(Optional.of(property));
        when(propertyMapper.toDto(property)).thenReturn(propertyDto);

        PropertyDto result = propertyService.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        when(propertyRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> propertyService.getById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testGetAll() {
        List<Property> properties = List.of(property);
        when(propertyRepository.findAll()).thenReturn(properties);
        when(propertyMapper.toDto(property)).thenReturn(propertyDto);

        List<PropertyDto> result = propertyService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateById() {
        when(propertyRepository.findById(1)).thenReturn(Optional.of(property));

        propertyDto.setId(1);
        propertyService.updateById(1, propertyDto);

        verify(propertyMapper, times(1)).updateEntity(propertyDto, property);
        verify(propertyRepository, times(1)).save(property);
    }

    @Test
    void testUpdateByIdNotFound() {
        when(propertyRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> propertyService.updateById(1, propertyDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        when(propertyRepository.findById(1)).thenReturn(Optional.of(property));

        propertyService.deleteById(1);

        verify(propertyRepository, times(1)).delete(property);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(propertyRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> propertyService.deleteById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testGetByUserId() {
        List<Property> properties = List.of(property);
        when(propertyRepository.findAllByOwnerId(1)).thenReturn(properties);
        when(propertyMapper.toDto(property)).thenReturn(propertyDto);

        List<PropertyDto> result = propertyService.getByUserId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetAllWithEssentialDetails() {
        List<Property> properties = List.of(property);
        when(propertyRepository.findAllWithEssentialDetails()).thenReturn(properties);
        when(propertyMapper.toDto(property)).thenReturn(propertyDto);

        List<PropertyDto> result = propertyService.getAllWithEssentialDetails();

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
