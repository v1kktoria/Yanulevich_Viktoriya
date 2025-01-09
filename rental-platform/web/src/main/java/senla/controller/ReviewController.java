package senla.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import senla.dicontainer.DIContainer;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Review;
import senla.service.PropertyService;
import senla.service.ReviewService;
import senla.service.UserService;
import senla.util.mapper.ReviewMapper;

import java.io.IOException;
import java.util.List;

@WebServlet("/reviews/*")
public class ReviewController extends HttpServlet {

    private ReviewService reviewService;

    private PropertyService propertyService;

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        reviewService = DIContainer.getBean(ReviewService.class);
        propertyService = DIContainer.getBean(PropertyService.class);
        userService = DIContainer.getBean(UserService.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Review review = ReviewMapper.fromRequest(request, propertyService, userService);
        reviewService.create(review)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.CREATION_FAILED));
        response.sendRedirect(request.getContextPath() + "/reviews");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            Integer reviewId = Integer.parseInt(idParam);
            reviewService.getById(reviewId)
                    .ifPresentOrElse(
                            review -> request.setAttribute("review", review),
                            () -> { throw new ServiceException(ServiceExceptionEnum.SEARCH_FAILED); }
                    );
        }
        List<Review> reviews = reviewService.getAll();
        request.setAttribute("reviews", reviews);
        request.getRequestDispatcher("/WEB-INF/jsp/reviews.jsp").forward(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Review review = ReviewMapper.fromRequest(request, propertyService, userService);
        reviewService.updateById(id, review);
        response.sendRedirect(request.getContextPath() + "/reviews");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        reviewService.deleteById(id);
        response.sendRedirect(request.getContextPath() + "/reviews");
    }
}

