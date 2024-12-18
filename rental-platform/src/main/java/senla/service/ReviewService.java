package senla.service;


import senla.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    Optional<Review> create(Review review);

    Optional<Review> getById(Integer id);

    List<Review> getAll();

    void updateById(Integer id, Review review);

    void deleteById(Integer id);
}
