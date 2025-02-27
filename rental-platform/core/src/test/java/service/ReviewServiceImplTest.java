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
import senla.service.impl.ReviewServiceImpl;
import senla.util.mappers.ReviewMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private ReviewDto reviewDto;

    private Review review;

    private Property property;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        reviewDto = new ReviewDto();
        reviewDto.setId(1);
        reviewDto.setPropertyId(1);
        reviewDto.setUserId(1);

        property = new Property();
        property.setId(1);

        user = new User();
        user.setId(1);

        review = new Review();
        review.setId(1);
        review.setUser(user);
    }

    @Test
    void testCreate() {
        when(propertyRepository.findById(1)).thenReturn(Optional.of(property));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(reviewMapper.toEntity(reviewDto, property, user)).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(review);
        when(reviewMapper.toDto(review)).thenReturn(reviewDto);

        ReviewDto createdReview = reviewService.create(reviewDto);

        assertNotNull(createdReview);
        assertEquals(1, createdReview.getId());
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    void testCreatePropertyNotFound() {
        when(propertyRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> reviewService.create(reviewDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testCreateUserNotFound() {
        when(propertyRepository.findById(1)).thenReturn(Optional.of(property));
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> reviewService.create(reviewDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testGetById() {
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));
        when(reviewMapper.toDto(review)).thenReturn(reviewDto);

        ReviewDto result = reviewService.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        when(reviewRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> reviewService.getById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testGetAll() {
        List<Review> reviews = List.of(review);
        when(reviewRepository.findAll()).thenReturn(reviews);
        when(reviewMapper.toDto(review)).thenReturn(reviewDto);

        List<ReviewDto> result = reviewService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateById() {
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        reviewDto.setId(1);
        reviewService.updateById(1, reviewDto);

        verify(reviewMapper, times(1)).updateEntity(reviewDto, review);
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    void testUpdateByIdNotFound() {
        when(reviewRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> reviewService.updateById(1, reviewDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        reviewService.deleteById(1);

        verify(reviewRepository, times(1)).delete(review);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(reviewRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> reviewService.deleteById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }
}
