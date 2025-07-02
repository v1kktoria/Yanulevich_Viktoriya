package senla.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import senla.aop.MeasureExecutionTime;
import senla.dto.ImageDto;
import senla.service.ImageService;

import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ImageDto> createImage(@RequestParam Integer propertyId, @RequestParam MultipartFile file) {
        log.info("Добавление изображения с данными: {}", file.getOriginalFilename());
        return ResponseEntity.status(201).body(imageService.create(propertyId, file));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ImageDto> getImage(@PathVariable("id") Integer id) {
        log.info("Запрос изображения с ID: {}", id);
        ImageDto image = imageService.getById(id);
        return ResponseEntity.ok(image);
    }

    @GetMapping("/property/{propertyId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<ImageDto>> getImagesForProperty(@PathVariable("propertyId") Integer propertyId) {
        log.info("Запрос изображений для недвижимости с ID: {}", propertyId);
        List<ImageDto> images = imageService.getImagesForProperty(propertyId);
        return ResponseEntity.ok(images);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@imageSecurityService.hasAccess(authentication, #id)")
    public ResponseEntity<String> updateImage(@PathVariable("id") Integer id, @RequestParam MultipartFile file) {
        log.info("Обновление изображения с ID: {} с новыми данными: {}", id, file.getOriginalFilename());
        imageService.updateById(id, file);
        return ResponseEntity.ok("Изображение успешно обновлено");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@imageSecurityService.hasAccess(authentication, #id)")
    public ResponseEntity<String> deleteImage(@PathVariable("id") Integer id) {
        log.info("Удаление изображения с ID: {}", id);
        imageService.deleteById(id);
        return ResponseEntity.ok("Изображение успешно удалено");
    }
}
