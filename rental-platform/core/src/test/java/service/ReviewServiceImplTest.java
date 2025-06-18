package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import senla.dto.ReviewDto;
import senla.exception.ServiceException;
import senla.model.Property;
import senla.model.Review;
import senla.model.User;
import senla.repository.PropertyRepository;
import senla.repository.ReviewRepository;
import senla.repository.UserRepository;
import senla.service.PropertyService;
import senla.service.impl.ReviewServiceImpl;
import senla.util.mappers.ReviewMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private PropertyService propertyService;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Integer reviewId = 1;
    private Integer propertyId = 100;
    private Integer userId = 200;
    private Review review;
    private ReviewDto reviewDto;
    private Property property;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        property = new Property();
        property.setId(propertyId);

        user = new User();
        user.setId(userId);

        review = new Review();
        review.setId(reviewId);
        review.setProperty(property);
        review.setUser(user);
        review.setCreatedAt(LocalDateTime.now());

        reviewDto = new ReviewDto();
        reviewDto.setId(reviewId);
        reviewDto.setPropertyId(propertyId);
        reviewDto.setUserId(userId);
    }

    @Test
    void testCreate() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(reviewRepository.existsByUserIdAndPropertyId(userId, propertyId)).thenReturn(false);
        when(reviewMapper.toEntity(reviewDto, property, user)).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(review);
        when(reviewMapper.toDto(review)).thenReturn(reviewDto);

        ReviewDto createdReview = reviewService.create(reviewDto);

        assertEquals(reviewId, createdReview.getId());
        verify(reviewRepository, times(1)).save(review);
        verify(propertyService, times(1)).updateRating(propertyId);
    }

    @Test
    void testCreatePropertyNotFound() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> reviewService.create(reviewDto));

        assertEquals("Объект с ID 100 не найден", exception.getMessage());
    }

    @Test
    void testCreateUserNotFound() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> reviewService.create(reviewDto));

        assertEquals("Объект с ID 100 не найден", exception.getMessage());
    }

    @Test
    void testCreateReviewAlreadyExists() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(reviewRepository.existsByUserIdAndPropertyId(userId, propertyId)).thenReturn(true);

        ServiceException exception = assertThrows(ServiceException.class, () -> reviewService.create(reviewDto));

        assertEquals("Отзыв пользователя с id 200 для недвижимости с id 100 уже существует", exception.getMessage());
    }

    @Test
    void testGetById() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(reviewMapper.toDto(review)).thenReturn(reviewDto);

        ReviewDto retrievedReview = reviewService.getById(reviewId);

        assertEquals(reviewId, retrievedReview.getId());
        verify(reviewRepository, times(1)).findById(reviewId);
    }

    @Test
    void testGetByIdNotFound() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> reviewService.getById(reviewId));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testGetAll() {
        when(reviewRepository.findAll()).thenReturn(List.of(review));
        when(reviewMapper.toDto(review)).thenReturn(reviewDto);

        List<ReviewDto> reviews = reviewService.getAll();

        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void testUpdateById() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        //when(reviewMapper.updateEntity(reviewDto, review)).thenReturn(review);

        reviewService.updateById(reviewId, reviewDto);

        verify(reviewRepository, times(1)).save(review);
        verify(propertyService, times(1)).updateRating(propertyId);
    }

    @Test
    void testUpdateByIdNotFound() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> reviewService.updateById(reviewId, reviewDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        reviewService.deleteById(reviewId);

        verify(reviewRepository, times(1)).delete(review);
        verify(propertyService, times(1)).updateRating(propertyId);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> reviewService.deleteById(reviewId));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }
}
