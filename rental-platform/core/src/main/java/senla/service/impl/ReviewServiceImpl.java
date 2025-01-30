package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dao.PropertyDao;
import senla.dao.ReviewDao;
import senla.dao.UserDao;
import senla.dto.ReviewDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Property;
import senla.model.Review;
import senla.model.User;
import senla.service.ReviewService;
import senla.util.mappers.ReviewMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@MeasureExecutionTime
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;

    private final PropertyDao propertyDao;

    private final UserDao userDao;

    private final ReviewMapper reviewMapper;

    @Override
    public ReviewDto create(ReviewDto reviewDto) {
        Property property = propertyDao.findById(reviewDto.getPropertyId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, reviewDto.getPropertyId()));

        User user = userDao.findById(reviewDto.getUserId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, reviewDto.getPropertyId()));


        Review review = reviewMapper.toEntity(reviewDto, property, user);
        Review savedReview = reviewDao.save(review);
        log.info("Отзыв с ID: {} успешно создан", savedReview.getId());
        return reviewMapper.toDto(savedReview);
    }

    @Override
    public ReviewDto getById(Integer id) {
        Review review = reviewDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        log.info("Отзыв с ID: {} успешно получен", id);
        return reviewMapper.toDto(review);
    }

    @Override
    public List<ReviewDto> getAll() {
        List<Review> reviews = reviewDao.findAll();
        reviews.forEach(Review::loadLazyFields);
        log.info("Найдено {} отзывов", reviews.size());
        return reviews.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateById(Integer id, ReviewDto reviewDto) {
        Review review = reviewDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        reviewDto.setId(id);
        reviewMapper.updateEntity(reviewDto, review);
        reviewDao.update(review);
        log.info("Отзыв с ID: {} успешно обновлен", id);
    }

    @Override
    public void deleteById(Integer id) {
        Review review = reviewDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        reviewDao.delete(review);
        log.info("Отзыв с ID: {} успешно удален", id);
    }
}
