package unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import senla.dto.ImageDto;
import senla.exception.ServiceException;
import senla.model.Image;
import senla.model.Property;
import senla.repository.ImageRepository;
import senla.repository.PropertyRepository;
import senla.service.impl.ImageServiceImpl;
import senla.util.mappers.ImageMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private ImageMapper imageMapper;

    @InjectMocks
    private ImageServiceImpl imageService;

    private Integer imageId = 1;
    private Integer propertyId = 100;
    private Image image;
    private ImageDto imageDto;
    private Property property;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        property = new Property();
        property.setId(propertyId);

        image = new Image();
        image.setId(imageId);
        image.setProperty(property);

        imageDto = new ImageDto();
        imageDto.setId(imageId);
        imageDto.setPropertyId(propertyId);
    }

    @Test
    void testCreate() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        when(imageMapper.toEntity(imageDto, property)).thenReturn(image);
        when(imageRepository.save(image)).thenReturn(image);
        when(imageMapper.toDto(image)).thenReturn(imageDto);

        ImageDto createdImage = imageService.create(imageDto);

        assertEquals(imageId, createdImage.getId());
        verify(imageRepository, times(1)).save(image);
    }

    @Test
    void testCreatePropertyNotFound() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> imageService.create(imageDto));

        assertEquals("Объект с ID 100 не найден", exception.getMessage());
    }

    @Test
    void testGetById() {
        when(imageRepository.findById(imageId)).thenReturn(Optional.of(image));
        when(imageMapper.toDto(image)).thenReturn(imageDto);

        ImageDto retrievedImage = imageService.getById(imageId);

        assertEquals(imageId, retrievedImage.getId());
        verify(imageRepository, times(1)).findById(imageId);
    }

    @Test
    void testGetByIdNotFound() {
        when(imageRepository.findById(imageId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> imageService.getById(imageId));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testGetImagesForProperty() {
        when(imageRepository.findByPropertyId(propertyId)).thenReturn(List.of(image));
        when(imageMapper.toDto(image)).thenReturn(imageDto);

        List<ImageDto> images = imageService.getImagesForProperty(propertyId);

        assertNotNull(images);
        assertEquals(1, images.size());
        verify(imageRepository, times(1)).findByPropertyId(propertyId);
    }

    @Test
    void testUpdateById() {
        when(imageRepository.findById(imageId)).thenReturn(Optional.of(image));

        imageService.updateById(imageId, imageDto);

        verify(imageRepository, times(1)).save(image);
    }

    @Test
    void testUpdateByIdNotFound() {
        when(imageRepository.findById(imageId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> imageService.updateById(imageId, imageDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        when(imageRepository.findById(imageId)).thenReturn(Optional.of(image));

        imageService.deleteById(imageId);

        verify(imageRepository, times(1)).delete(image);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(imageRepository.findById(imageId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> imageService.deleteById(imageId));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }
}
