package senla.service.impl;

import senla.dao.impl.ReviewDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Review;
import senla.service.ReviewService;
import senla.util.TransactionManager;
import senla.util.validator.ReviewValidator;

import java.util.List;
import java.util.Optional;

@Component
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDAOImpl reviewDAO;

    @Override
    public Optional<Review> create(Review review) {
        return TransactionManager.executeInTransaction(() -> {
            ReviewValidator.validate(review);
            return Optional.ofNullable(reviewDAO.save(review));
        });
    }

    @Override
    public Optional<Review> getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return Optional.ofNullable(reviewDAO.findById(id));
        });
    }

    @Override
    public List<Review> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            List<Review> reviews = reviewDAO.findAll();
            reviews.forEach(Review::loadLazyFields);
            return reviews;
        });
    }

    @Override
    public void updateById(Integer id, Review review) {
        TransactionManager.executeInTransaction(() -> {
            review.setId(id);
            ReviewValidator.validate(review);
            reviewDAO.update(review);
            return Optional.empty();
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            reviewDAO.deleteById(id);
            return Optional.empty();
        });
    }
}
