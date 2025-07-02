package unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import senla.dto.PropertyDto;
import senla.exception.ServiceException;
import senla.model.Property;
import senla.model.Review;
import senla.model.User;
import senla.model.constant.PropertyType;
import senla.repository.PropertyRepository;
import senla.repository.UserRepository;
import senla.service.impl.PropertyServiceImpl;
import senla.util.mappers.PropertyMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PropertyServiceImplTest {

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PropertyMapper propertyMapper;

    @InjectMocks
    private PropertyServiceImpl propertyService;

    private Integer propertyId = 1;
    private Property property;
    private PropertyDto propertyDto;
    private User owner;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        property = new Property();
        property.setId(propertyId);
        owner = new User();
        owner.setId(1);
        property.setOwner(owner);
        propertyDto = new PropertyDto();
        propertyDto.setId(propertyId);
        propertyDto.setOwnerId(1);
    }

    @Test
    void testCreate() {
        when(propertyMapper.toEntity(propertyDto, property.getOwner())).thenReturn(property);
        when(propertyRepository.save(property)).thenReturn(property);
        when(propertyMapper.toDto(property)).thenReturn(propertyDto);
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(owner));

        PropertyDto createdProperty = propertyService.create(propertyDto);

        assertEquals(propertyId, createdProperty.getId());
        assertEquals(owner.getId(), createdProperty.getOwnerId());
        verify(propertyRepository, times(1)).save(property);
    }

    @Test
    void testGetById() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        when(propertyMapper.toDto(property)).thenReturn(propertyDto);

        PropertyDto retrievedProperty = propertyService.getById(propertyId);

        assertEquals(propertyId, retrievedProperty.getId());
        verify(propertyRepository, times(1)).findById(propertyId);
    }

    @Test
    void testGetByIdNotFound() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> propertyService.getById(propertyId));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testGetByUserId() {
        when(propertyRepository.findAllByOwnerId(eq(propertyId), ArgumentMatchers.any(Sort.class))).thenReturn(List.of(property));
        when(propertyMapper.toDto(property)).thenReturn(propertyDto);

        List<PropertyDto> properties = propertyService.getByUserId(propertyId);

        assertNotNull(properties);
        assertEquals(1, properties.size());
        verify(propertyRepository, times(1)).findAllByOwnerId(eq(propertyId), ArgumentMatchers.any(Sort.class));
    }

    @Test
    void testGetAll() {
        when(propertyRepository.findAll(ArgumentMatchers.any(Sort.class))).thenReturn(List.of(property));
        when(propertyMapper.toDto(property)).thenReturn(propertyDto);

        List<PropertyDto> properties = propertyService.getAll();

        assertNotNull(properties);
        assertEquals(1, properties.size());
        verify(propertyRepository, times(1)).findAll(ArgumentMatchers.any(Sort.class));
    }

    @Test
    void testUpdateById() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));

        propertyService.updateById(propertyId, propertyDto);

        verify(propertyRepository, times(1)).save(property);
    }

    @Test
    void testUpdateByIdNotFound() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> propertyService.updateById(propertyId, propertyDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));

        propertyService.deleteById(propertyId);

        verify(propertyRepository, times(1)).delete(property);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> propertyService.deleteById(propertyId));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testSearchProperties() {
        when(propertyRepository.searchProperties(eq(PropertyType.APARTMENT), eq(100.0), eq(500.0), eq(1), eq(3), eq("Test"), ArgumentMatchers.any(Sort.class)))
                .thenReturn(List.of(property));
        when(propertyMapper.toDto(property)).thenReturn(propertyDto);

        List<PropertyDto> properties = propertyService.searchProperties(PropertyType.APARTMENT, 100.0, 500.0, 1, 3, "Test");

        assertNotNull(properties);
        assertEquals(1, properties.size());
        verify(propertyRepository, times(1))
                .searchProperties(eq(PropertyType.APARTMENT), eq(100.0), eq(500.0), eq(1), eq(3), eq("Test"), ArgumentMatchers.any(Sort.class));
    }

    @Test
    void testUpdateRating() {
        Review review1 = new Review();
        review1.setRating(5);

        Review review2 = new Review();
        review2.setRating(3);

        property.setReviews(List.of(review1, review2));

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        when(propertyRepository.save(property)).thenReturn(property);

        propertyService.updateRating(propertyId);

        assertEquals(4.0, property.getRating());
        verify(propertyRepository, times(1)).save(property);
    }

    @Test
    void testUpdateRatingNotFound() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> propertyService.updateRating(propertyId));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }
}
