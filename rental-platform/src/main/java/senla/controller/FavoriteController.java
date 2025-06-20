package senla.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import senla.aop.MeasureExecutionTime;
import senla.dto.PropertyDto;
import senla.service.FavoriteService;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{userId}/{propertyId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<String> addToFavorites(@PathVariable Integer userId, @PathVariable Integer propertyId) {
        log.info("Добавление недвижимости ID={} в избранное пользователя ID={}", propertyId, userId);
        favoriteService.addPropertyToFavorites(userId, propertyId);
        return ResponseEntity.ok("Недвижимость добавлена в избранное");
    }

    @DeleteMapping("/{userId}/{propertyId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<String> removeFromFavorites(@PathVariable Integer userId, @PathVariable Integer propertyId) {
        log.info("Удаление недвижимости ID={} из избранного пользователя ID={}", propertyId, userId);
        favoriteService.removePropertyFromFavorites(userId, propertyId);
        return ResponseEntity.ok("Недвижимость удалена из избранного");
    }

    @GetMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<List<PropertyDto>> getFavorites(@PathVariable Integer userId) {
        log.info("Запрос избранных объектов недвижимости для пользователя ID={}", userId);
        return ResponseEntity.ok(favoriteService.getFavoritesByUserId(userId));
    }
}
