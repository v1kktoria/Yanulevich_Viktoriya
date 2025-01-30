package senla.dao.impl;

import org.springframework.stereotype.Repository;
import senla.dao.AbstractDao;
import senla.dao.ReviewDao;
import senla.model.Review;

@Repository
public class ReviewDaoImpl extends AbstractDao<Review, Integer> implements ReviewDao {
    @Override
    protected Class<Review> getEntityClass() {
        return Review.class;
    }
}
