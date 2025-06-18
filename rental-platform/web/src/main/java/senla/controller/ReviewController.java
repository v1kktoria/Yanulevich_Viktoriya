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
import senla.dto.ReviewDto;
import senla.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReviewDto> createReview(@RequestBody @Valid ReviewDto reviewDto) {
        log.info("Создание нового отзыва: {}", reviewDto);
        ReviewDto review = reviewService.create(reviewDto);
        return ResponseEntity.status(201).body(review);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        log.info("Запрос на получение всех отзывов");
        List<ReviewDto> reviews = reviewService.getAll();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ReviewDto> getReview(@PathVariable("id") Integer id) {
        log.info("Запрос на получение отзыва с ID: {}", id);
        ReviewDto review = reviewService.getById(id);
        return ResponseEntity.ok(review);
    }

    @PutMapping("/{id}")
    @PreAuthorize("authentication.principal.id == #reviewDto.userId")
    public ResponseEntity<String> updateReview(@PathVariable("id") Integer id, @RequestBody @Valid ReviewDto reviewDto) {
        log.info("Обновление отзыва с ID: {} с новыми данными: {}", id, reviewDto);
        reviewService.updateById(id, reviewDto);
        return ResponseEntity.ok("Отзыв успешно обновлен");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == @reviewServiceImpl.getById(#id).userId")
    public ResponseEntity<String> deleteReview(@PathVariable("id") Integer id) {
        log.info("Удаление отзыва с ID: {}", id);
        reviewService.deleteById(id);
        return ResponseEntity.ok("Отзыв успешно удален");
    }
}