package unit.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import senla.controller.ReviewController;
import senla.dto.ReviewDto;
import senla.service.ReviewService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private ReviewDto reviewDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        reviewDto = new ReviewDto();
        reviewDto.setId(1);
        reviewDto.setUserId(1);
        reviewDto.setComment("Test");
    }

    @Test
    void testCreateReview() {
        when(reviewService.create(reviewDto)).thenReturn(reviewDto);

        ResponseEntity<ReviewDto> response = reviewController.createReview(reviewDto);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Test", response.getBody().getComment());
    }

    @Test
    void testGetAllReviews() {
        when(reviewService.getAll()).thenReturn(List.of(reviewDto));

        ResponseEntity<List<ReviewDto>> response = reviewController.getAllReviews();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Test", response.getBody().get(0).getComment());
    }

    @Test
    void testGetReview() {
        when(reviewService.getById(1)).thenReturn(reviewDto);

        ResponseEntity<ReviewDto> response = reviewController.getReview(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test", response.getBody().getComment());
    }

    @Test
    void testUpdateReview() {
        doNothing().when(reviewService).updateById(1, reviewDto);

        ResponseEntity<String> response = reviewController.updateReview(1, reviewDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Отзыв успешно обновлен", response.getBody());
    }

    @Test
    void testDeleteReview() {
        doNothing().when(reviewService).deleteById(1);

        ResponseEntity<String> response = reviewController.deleteReview(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Отзыв успешно удален", response.getBody());
    }
}
