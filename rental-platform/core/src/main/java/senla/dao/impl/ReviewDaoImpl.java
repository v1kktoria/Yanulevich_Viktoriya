package senla.dao.impl;

import senla.dao.AbstractDao;
import senla.dao.ReviewDao;
import senla.dicontainer.annotation.Component;
import senla.model.Review;

@Component
public class ReviewDaoImpl extends AbstractDao<Review, Integer> implements ReviewDao {
    @Override
    protected Class<Review> getEntityClass() {
        return Review.class;
    }
}
