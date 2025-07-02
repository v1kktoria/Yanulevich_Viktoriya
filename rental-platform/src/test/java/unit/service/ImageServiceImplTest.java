package unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import senla.dto.ImageDto;
import senla.exception.ServiceException;
import senla.model.Image;
import senla.model.Property;
import senla.repository.ImageRepository;
import senla.repository.PropertyRepository;
import senla.service.MinioService;
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

    @Mock
    private MinioService minioService;

    @InjectMocks
    private ImageServiceImpl imageService;

    private Integer imageId = 1;
    private Integer propertyId = 100;
    private Image image;
    private ImageDto imageDto;
    private Property property;
    private MultipartFile mockFile;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        property = new Property();
        property.setId(propertyId);

        image = new Image();
        image.setId(imageId);
        image.setProperty(property);
        image.setImageUrl("http://example.com/image.jpg");


        imageDto = new ImageDto();
        imageDto.setId(imageId);
        imageDto.setPropertyId(propertyId);
        imageDto.setImageUrl("http://example.com/image.jpg");

    }

    @Test
    void testCreate() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        when(minioService.upload(mockFile)).thenReturn("http://example.com/image.jpg");
        when(imageRepository.save(any(Image.class))).thenAnswer(invocation -> {
            Image img = invocation.getArgument(0);
            img.setId(imageId);
            return img;
        });
        when(imageMapper.toDto(any(Image.class))).thenReturn(imageDto);

        ImageDto createdImage = imageService.create(propertyId, mockFile);

        assertEquals(imageId, createdImage.getId());
        assertEquals("http://example.com/image.jpg", createdImage.getImageUrl());

        verify(imageRepository, times(1)).save(image);
    }

    @Test
    void testCreatePropertyNotFound() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> imageService.create(propertyId, mockFile));

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
        when(minioService.upload(mockFile)).thenReturn("http://example.com/new-image.jpg");
        when(imageRepository.save(any(Image.class))).thenReturn(image);

        imageService.updateById(imageId, mockFile);

        verify(imageRepository).findById(imageId);
        verify(minioService).upload(mockFile);
        verify(imageRepository, times(1)).save(image);
    }

    @Test
    void testUpdateByIdNotFound() {
        when(imageRepository.findById(imageId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> imageService.updateById(imageId, mockFile));

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
