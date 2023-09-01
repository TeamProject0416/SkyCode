package teamproject.skycode.review;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//@RequestMapping("/review")
@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;

    @GetMapping(value = "/reviewSub")
    public String main(ReviewSearchDto reviewSearchDto, Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);   /* 페이지당 6개씩 출력 */
        Page<MainReviewDto> reviewList = reviewService.getMainReviewPage(reviewSearchDto, pageable);

        model.addAttribute("reviews", reviewList);
        model.addAttribute("reviewSearchDto", reviewSearchDto);
        model.addAttribute("maxPage", 5);

        return "review/reviewSub";
    }

//    @GetMapping(value = "/reviewSub")
//    public String reviewSub(Model model) {
//
//        List<Review> reviewList = reviewService.findReviews();
//        model.addAttribute("reviews", reviewList);
////        reviewEntity.setRegTime(LocalDateTime.now());
//        return "review/reviewSub";
//    }

    @PostMapping(value = "/review/create")
    public String reviewCreate(ReviewFormDto form) {

        Review review = form.toEntity();
        Review saved = reviewRepository.save(review);
        return "redirect:/reviewSub";
    }

    @GetMapping(value = "/reviewShow/{reviewId}")
    public String reviewShow(@PathVariable("reviewId") Long reviewId, Model model) {
//        Review reviewEntityShow = reviewRepository.findById(id).orElse(null);
//        model.addAttribute("reviewShow", reviewEntityShow);
        ReviewFormDto reviewFormDto = reviewService.getItemDtl(reviewId);
        model.addAttribute("reviewShow", reviewFormDto);
        return "review/reviewShow";
    }

    @GetMapping(value = "/review/{id}/edit")
    public String reviewEdit(@PathVariable Long id, Model model) {
        Review reviewEntityEdit = reviewRepository.findById(id).orElse(null);
        model.addAttribute("reviewEdit", reviewEntityEdit);
        return "review/editReview";
    }

    @PostMapping(value = "/review/update")
    public String update(ReviewFormDto form) {
        Review reviewEntity = form.toEntity();
        Review target = reviewRepository.findById(reviewEntity.getId()).orElse(null);

//        2-2. 기존 데이터가 있다면, 값을 갱신
        if(target != null) {
            reviewRepository.save(reviewEntity);
        }
        return "redirect:/reviewShow/" + reviewEntity.getId();
    }






    //    임시
    @GetMapping(value = "/newReview")
    public String newReviewForm(Model model) {
        model.addAttribute("reviewFormDto", new ReviewFormDto());
        return "review/newReview";
    }

//    @GetMapping(value = "/admin/item/{itemId}")
//    public String itemDtl(@PathVariable("itemId") Long itemId, Model model) {
//        try {
//            ReviewFormDto reviewFormDto = reviewService.getItemDtl(itemId);
//            model.addAttribute("reviewFormDto", reviewFormDto);
//        } catch (EntityNotFoundException e) {
//            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
//            model.addAttribute("reviewFormDto", new ReviewFormDto());
//            return "item/itemForm";
//
//        }
//        return "item/itemForm";
//    }

}

