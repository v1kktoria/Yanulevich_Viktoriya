package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dto.ReviewDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Property;
import senla.model.Review;
import senla.model.User;
import senla.repository.PropertyRepository;
import senla.repository.ReviewRepository;
import senla.repository.UserRepository;
import senla.service.PropertyService;
import senla.service.ReviewService;
import senla.util.mappers.ReviewMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final PropertyRepository propertyRepository;

    private final UserRepository userRepository;

    private final ReviewMapper reviewMapper;

    private final PropertyService propertyService;

    @Transactional
    @Override
    public ReviewDto create(ReviewDto reviewDto) {
        Property property = propertyRepository.findById(reviewDto.getPropertyId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, reviewDto.getPropertyId()));

        User user = userRepository.findById(reviewDto.getUserId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, reviewDto.getPropertyId()));


        if (reviewRepository.existsByUserIdAndPropertyId(user.getId(), property.getId())) {
            throw new ServiceException(ServiceExceptionEnum.REVIEW_ALREADY_EXIST, user.getId(), property.getId());
        }

        Review review = reviewMapper.toEntity(reviewDto, property, user);
        review.setCreatedAt(LocalDateTime.now());
        Review savedReview = reviewRepository.save(review);
        propertyService.updateRating(property.getId());
        log.info("Отзыв с ID: {} успешно создан", savedReview.getId());
        return reviewMapper.toDto(savedReview);
    }

    @Override
    public ReviewDto getById(Integer id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        log.info("Отзыв с ID: {} успешно получен", id);
        return reviewMapper.toDto(review);
    }

    @Override
    public List<ReviewDto> getAll() {
        List<Review> reviews = reviewRepository.findAll();
        log.info("Найдено {} отзывов", reviews.size());
        return reviews.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateById(Integer id, ReviewDto reviewDto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        reviewDto.setId(id);
        reviewMapper.updateEntity(reviewDto, review);
        reviewRepository.save(review);
        propertyService.updateRating(review.getProperty().getId());
        log.info("Отзыв с ID: {} успешно обновлен", id);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        reviewRepository.delete(review);
        propertyService.updateRating(review.getProperty().getId());
        log.info("Отзыв с ID: {} успешно удален", id);
    }
}
