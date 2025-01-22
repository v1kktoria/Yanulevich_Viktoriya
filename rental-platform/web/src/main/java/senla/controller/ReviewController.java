package senla.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import senla.model.Review;
import senla.service.ReviewService;

@Controller
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public String createReview(@ModelAttribute @Valid Review review) {
        reviewService.create(review);
        return "redirect:/reviews";
    }

    @GetMapping
    public String getAllReviews(Model model) {
        model.addAttribute("reviews", reviewService.getAll());
        return "reviews";
    }

    @GetMapping("/review")
    public String getReview(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("review", reviewService.getById(id));
        return "reviews";
    }

    @PutMapping("/{id}")
    public String updateReview(@PathVariable("id") Integer id, @ModelAttribute @Valid Review review) {
        reviewService.updateById(id, review);
        return "redirect:/reviews";
    }

    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable("id") Integer id) {
        reviewService.deleteById(id);
        return "redirect:/reviews";
    }
}