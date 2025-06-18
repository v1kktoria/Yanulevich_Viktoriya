package service;

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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ImageServiceImplTest {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private ImageMapper imageMapper;

    @InjectMocks
    private ImageServiceImpl imageService;

    private ImageDto imageDto;

    private Image image;

    private Property property;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        imageDto = new ImageDto();
        imageDto.setId(1);
        imageDto.setPropertyId(1);

        image = new Image();
        image.setId(1);

        property = new Property();
        property.setId(1);
    }

    @Test
    void testCreate() {
        when(propertyRepository.findById(1)).thenReturn(Optional.of(property));
        when(imageMapper.toEntity(imageDto, property)).thenReturn(image);
        when(imageRepository.save(image)).thenReturn(image);
        when(imageMapper.toDto(image)).thenReturn(imageDto);

        ImageDto createdImage = imageService.create(imageDto);

        assertNotNull(createdImage);
        assertEquals(1, createdImage.getId());
        verify(imageRepository, times(1)).save(image);
    }

    @Test
    void testGetById() {
        when(imageRepository.findById(1)).thenReturn(Optional.of(image));
        when(imageMapper.toDto(image)).thenReturn(imageDto);

        ImageDto result = imageService.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        when(imageRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> imageService.getById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testUpdateById() {
        when(imageRepository.findById(1)).thenReturn(Optional.of(image));

        imageDto.setId(1);
        imageService.updateById(1, imageDto);

        verify(imageMapper, times(1)).updateEntity(imageDto, image);
        verify(imageRepository, times(1)).save(image);
    }

    @Test
    void testUpdateByIdNotFound() {
        when(imageRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> imageService.updateById(1, imageDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        when(imageRepository.findById(1)).thenReturn(Optional.of(image));

        imageService.deleteById(1);

        verify(imageRepository, times(1)).delete(image);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(imageRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> imageService.deleteById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }
}
