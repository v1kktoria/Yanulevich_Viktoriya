package senla.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import senla.dao.ReviewDao;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Review;
import senla.service.ReviewService;
import senla.util.TransactionManager;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;

    @Override
    public Review create(Review review) {
        return TransactionManager.executeInTransaction(() -> {
            return reviewDao.save(review);
        });
    }

    @Override
    public Review getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return reviewDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        });
    }

    @Override
    public List<Review> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            List<Review> reviews = reviewDao.findAll();
            reviews.forEach(Review::loadLazyFields);
            return reviews;
        });
    }

    @Override
    public void updateById(Integer id, Review review) {
        TransactionManager.executeInTransaction(() -> {
            review.setId(id);
            reviewDao.update(review);
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            Review review = reviewDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
            reviewDao.delete(review);
        });
    }
}
