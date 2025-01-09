package senla.dao.impl;

import senla.dao.AbstractDAO;
import senla.dicontainer.annotation.Component;
import senla.model.Review;

@Component
public class ReviewDAOImpl extends AbstractDAO<Review, Integer> {
    @Override
    protected Class<Review> getEntityClass() {
        return Review.class;
    }
}
