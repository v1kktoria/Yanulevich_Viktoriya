package senla.util.mapper;

import jakarta.servlet.http.HttpServletRequest;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Review;
import senla.service.PropertyService;
import senla.service.UserService;


public class ReviewMapper {

    public static Review fromRequest(HttpServletRequest request, PropertyService propertyService, UserService userService) {
        Integer propertyId = Integer.parseInt(request.getParameter("property_id"));
        Integer userId = Integer.parseInt(request.getParameter("user_id"));
        Integer rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");

        return Review.builder()
                .property(propertyService.getById(propertyId)
                        .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.SEARCH_FAILED)))
                .user(userService.getById(userId)
                        .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.SEARCH_FAILED)))
                .rating(rating)
                .comment(comment)
                .createdAt(new java.sql.Timestamp(System.currentTimeMillis()))
                .deleted(false)
                .build();
    }
}
