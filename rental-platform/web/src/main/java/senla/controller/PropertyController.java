package senla.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import senla.dto.PropertyDto;
import senla.service.PropertyService;

import java.util.List;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping
    public ResponseEntity<PropertyDto> createProperty(@RequestBody @Valid PropertyDto propertyDto) {
        log.info("Создание новой недвижимости с данными: {}", propertyDto);
        PropertyDto property = propertyService.create(propertyDto);
        return ResponseEntity.status(201).body(property);
    }

    @GetMapping
    public ResponseEntity<List<PropertyDto>> getAllProperties() {
        log.info("Запрос на получение всех объектов недвижимости");
        List<PropertyDto> properties = propertyService.getAll();
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyDto> getProperty(@PathVariable("id") Integer id) {
        log.info("Запрос на получение недвижимости с ID: {}", id);
        PropertyDto property = propertyService.getById(id);
        return ResponseEntity.ok(property);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (authentication.principal.id == #propertyDto.ownerId)")
    public ResponseEntity<String> updateProperty(@PathVariable("id") Integer id, @RequestBody @Valid PropertyDto propertyDto) {
        log.info("Обновление недвижимости с ID: {} с новыми данными: {}", id, propertyDto);
        propertyService.updateById(id, propertyDto);
        return ResponseEntity.ok("Недвижимость успешно обновлена");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (authentication.principal.id == @propertyServiceImpl.getById(#id).ownerId)")
    public ResponseEntity<String> deleteProperty(@PathVariable("id") Integer id) {
        log.info("Удаление недвижимости с ID: {}", id);
        propertyService.deleteById(id);
        return ResponseEntity.ok("Недвижимость успешно удалена");
    }
}
