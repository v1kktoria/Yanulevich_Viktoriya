package service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import senla.Main;
import senla.dto.ReviewDto;
import senla.exception.ServiceException;
import senla.service.ReviewService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = Main.class)
@RequiredArgsConstructor
@Transactional
@ActiveProfiles("test")
public class ReviewServiceIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    private ReviewDto reviewDto;

    @BeforeEach
    public void setUp() {
        reviewDto = new ReviewDto();
        reviewDto.setUserId(1);
        reviewDto.setPropertyId(1);
        reviewDto.setRating(5);
    }

    @Test
    public void testCreateReview() {
        ReviewDto createdReview = reviewService.create(reviewDto);
        assertThat(createdReview).isNotNull();
        assertThat(createdReview.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetReviewById() {
        ReviewDto createdReview = reviewService.create(reviewDto);
        ReviewDto fetchedReview = reviewService.getById(createdReview.getId());
        assertThat(fetchedReview).isNotNull();
        assertThat(fetchedReview.getId()).isEqualTo(createdReview.getId());
    }

    @Test
    public void testGetReviewByIdNotFound() {
        assertThrows(ServiceException.class, () -> reviewService.getById(9999));
    }

    @Test
    public void testGetAllReviews() {
        reviewService.create(reviewDto);
        List<ReviewDto> allReviews = reviewService.getAll();
        assertThat(allReviews).isNotEmpty();
    }

    @Test
    public void testUpdateReview() {
        ReviewDto createdReview = reviewService.create(reviewDto);
        createdReview.setRating(3);
        reviewService.updateById(createdReview.getId(), createdReview);

        ReviewDto updatedReview = reviewService.getById(createdReview.getId());
        assertThat(updatedReview.getRating()).isEqualTo(3);
    }

    @Test
    public void testDeleteReview() {
        ReviewDto createdReview = reviewService.create(reviewDto);
        reviewService.deleteById(createdReview.getId());

        assertThrows(ServiceException.class, () -> reviewService.getById(createdReview.getId()));
    }
}