package senla.service;


import senla.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    ReviewDto create(ReviewDto reviewDto);

    ReviewDto getById(Integer id);

    List<ReviewDto> getAll();

    void updateById(Integer id, ReviewDto reviewDto);

    void deleteById(Integer id);
}
