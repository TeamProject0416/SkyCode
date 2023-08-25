package teamproject.skycode.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

//@RequestMapping("/review")
@Controller
public class reviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping(value = "/reviewSub")
    public String reviewSub(){
        return "review/reviewSub";
    }

    @GetMapping(value = "/newReview")
    public String newReviewForm(){
        return "review/newReview";
    }
    @PostMapping(value = "/review/create")
    public String createReview(ReviewForm form){
        Review review = form.toEntity();
        Review saved = reviewRepository.save(review);
        return "redirect:/review/" + saved.getId();
    }

    @GetMapping(value = "/review/{id}")
    public String userReview(@PathVariable Long id, Model model) {
        Review reviewEntity = reviewRepository.findById(id).orElse(null);

        reviewEntity.setRegTime(LocalDateTime.now());
        model.addAttribute("reviews", reviewEntity);
        return "review/reviewSub";
    }
}

