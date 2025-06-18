package unit.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import senla.controller.ImageController;
import senla.dto.ImageDto;
import senla.service.ImageService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ImageControllerTest {

    @Mock
    private ImageService imageService;

    @InjectMocks
    private ImageController imageController;

    private ImageDto imageDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        imageDto = new ImageDto();
        imageDto.setId(1);
        imageDto.setPropertyId(1);
        imageDto.setImageUrl("http://example.com/image.jpg");
    }

    @Test
    void testCreateImage() {
        when(imageService.create(imageDto)).thenReturn(imageDto);

        ResponseEntity<ImageDto> response = imageController.createImage(imageDto);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("http://example.com/image.jpg", response.getBody().getImageUrl());
    }

    @Test
    void testGetImage() {
        when(imageService.getById(1)).thenReturn(imageDto);

        ResponseEntity<ImageDto> response = imageController.getImage(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("http://example.com/image.jpg", response.getBody().getImageUrl());
    }

    @Test
    void testGetImagesForProperty() {
        when(imageService.getImagesForProperty(1)).thenReturn(List.of(imageDto));

        ResponseEntity<List<ImageDto>> response = imageController.getImagesForProperty(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("http://example.com/image.jpg", response.getBody().get(0).getImageUrl());
    }

    @Test
    void testUpdateImage() {
        doNothing().when(imageService).updateById(1, imageDto);

        ResponseEntity<String> response = imageController.updateImage(1, imageDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Изображение успешно обновлено", response.getBody());
    }

    @Test
    void testDeleteImage() {
        doNothing().when(imageService).deleteById(1);

        ResponseEntity<String> response = imageController.deleteImage(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Изображение успешно удалено", response.getBody());
    }
}