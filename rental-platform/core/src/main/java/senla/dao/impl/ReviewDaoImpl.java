package senla.dao.impl;

import senla.dao.AbstractDao;
import senla.dao.ReviewDao;
import senla.model.Review;

public class ReviewDaoImpl extends AbstractDao<Review, Integer> implements ReviewDao {
    @Override
    protected Class<Review> getEntityClass() {
        return Review.class;
    }
}
