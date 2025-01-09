package senla.service.impl;

import senla.dao.impl.ReviewDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Review;
import senla.service.ReviewService;
import senla.util.validator.ReviewValidator;

import java.util.List;
import java.util.Optional;

@Component
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDAOImpl reviewDAO;

    @Override
    public Optional<Review> create(Review review) {
        ReviewValidator.validate(review);
        return Optional.ofNullable(reviewDAO.create(review));
    }

    @Override
    public Optional<Review> getById(Integer id) {
        return Optional.ofNullable(reviewDAO.getByParam(id));
    }

    @Override
    public List<Review> getAll() {
        return reviewDAO.getAll();
    }

    @Override
    public void updateById(Integer id, Review review) {
        ReviewValidator.validate(review);
        reviewDAO.updateById(id, review);
    }

    @Override
    public void deleteById(Integer id) {
        reviewDAO.deleteById(id);
    }
}
