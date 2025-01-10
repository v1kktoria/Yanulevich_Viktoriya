package senla.service.impl;

import senla.dao.ReviewDao;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Review;
import senla.service.ReviewService;
import senla.util.TransactionManager;
import senla.util.validator.ReviewValidator;

import java.util.List;

@Component
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Override
    public Review create(Review review) {
        return TransactionManager.executeInTransaction(() -> {
            ReviewValidator.validate(review);
            return reviewDao.save(review);
        });
    }

    @Override
    public Review getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return reviewDao.findById(id);
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
            ReviewValidator.validate(review);
            reviewDao.update(review);
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            reviewDao.deleteById(id);
        });
    }
}
