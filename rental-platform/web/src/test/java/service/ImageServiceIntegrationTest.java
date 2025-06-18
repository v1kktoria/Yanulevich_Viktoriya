package service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import senla.Main;
import senla.dto.ImageDto;
import senla.exception.ServiceException;
import senla.service.ImageService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = Main.class)
@RequiredArgsConstructor
@Transactional
@ActiveProfiles("test")
public class ImageServiceIntegrationTest {

    @Autowired
    private ImageService imageService;

    private ImageDto imageDto;

    @BeforeEach
    public void setUp() {
        imageDto = new ImageDto();
        imageDto.setPropertyId(1);
        imageDto.setFilepath("images/property_1.jpg");
    }

    @Test
    public void testCreateImage() {
        ImageDto createdImage = imageService.create(imageDto);
        assertThat(createdImage).isNotNull();
        assertThat(createdImage.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetImageById() {
        ImageDto createdImage = imageService.create(imageDto);
        ImageDto fetchedImage = imageService.getById(createdImage.getId());
        assertThat(fetchedImage).isNotNull();
        assertThat(fetchedImage.getId()).isEqualTo(createdImage.getId());
    }

    @Test
    public void testGetImageByIdNotFound() {
        assertThrows(ServiceException.class, () -> imageService.getById(9999));
    }

    @Test
    public void testGetAllImages() {
        imageService.create(imageDto);
        assertThat(imageService.getAll()).isNotEmpty();
    }

    @Test
    public void testUpdateImage() {
        ImageDto createdImage = imageService.create(imageDto);
        createdImage.setFilepath("images/property_2.jpg");
        imageService.updateById(createdImage.getId(), createdImage);

        ImageDto updatedImage = imageService.getById(createdImage.getId());
        assertThat(updatedImage.getFilepath()).isEqualTo("images/property_2.jpg");
    }

    @Test
    public void testDeleteImage() {
        ImageDto createdImage = imageService.create(imageDto);
        imageService.deleteById(createdImage.getId());

        assertThrows(ServiceException.class, () -> imageService.getById(createdImage.getId()));
    }
}