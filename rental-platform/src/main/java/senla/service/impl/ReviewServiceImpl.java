package senla.service.impl;

import senla.dao.impl.ReviewDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Review;
import senla.service.ReviewService;

import java.util.List;

@Component
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDAOImpl reviewDAO;

    @Override
    public Review create(Review review) {
        validate(review);
        return reviewDAO.create(review);
    }

    @Override
    public Review getById(Integer id) {
        return reviewDAO.getByParam(id);
    }

    @Override
    public List<Review> getAll() {
        return reviewDAO.getAll();
    }

    @Override
    public void updateById(Integer id, Review review) {
        validate(review);
        reviewDAO.updateById(id, review);
    }

    @Override
    public void deleteById(Integer id) {
        reviewDAO.deleteById(id);
    }

    private void validate(Review review) {
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Рейтинг должен быть в диапазоне от 1 до 5");
        }
    }
}
