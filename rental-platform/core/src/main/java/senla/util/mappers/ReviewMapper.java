package senla.util.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import senla.dto.ReviewDto;
import senla.model.Property;
import senla.model.Review;
import senla.model.User;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

    private final ModelMapper modelMapper;

    public ReviewDto toDto(Review review) {
        ReviewDto reviewDto = modelMapper.map(review, ReviewDto.class);

        reviewDto.setPropertyId(review.getProperty() != null ? review.getProperty().getId() : null);
        reviewDto.setUserId(review.getUser() != null ? review.getUser().getId() : null);


        return reviewDto;
    }

    public Review toEntity(ReviewDto reviewDto, Property property, User user) {
        Review review = modelMapper.map(reviewDto, Review.class);
        review.setProperty(property);
        review.setUser(user);
        return review;
    }

    public void updateEntity(ReviewDto reviewDto, Review review) {
        modelMapper.map(reviewDto, review);
    }
}
