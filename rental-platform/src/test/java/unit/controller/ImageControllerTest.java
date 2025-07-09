package unit.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
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

    private MockMultipartFile mockFile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        imageDto = new ImageDto();
        imageDto.setId(1);
        imageDto.setPropertyId(1);
        imageDto.setImageUrl("http://example.com/image.jpg");

        mockFile = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/jpeg",
                "fake-image-content".getBytes()
        );
    }

    @Test
    void testCreateImage() {
        when(imageService.create(1, mockFile)).thenReturn(imageDto);

        ResponseEntity<ImageDto> response = imageController.createImage(1, mockFile);

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
        doNothing().when(imageService).updateById(1, mockFile);

        ResponseEntity<String> response = imageController.updateImage(1, mockFile);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Изображение успешно обновлено", response.getBody());

        verify(imageService, times(1)).updateById(1, mockFile);
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