package teamproject.skycode.review;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

//@RequestMapping("/review")
@Controller
@RequiredArgsConstructor
@RequestMapping("review")
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;
    private final CommentService commentService;

//    리뷰 리스트 보기
    @GetMapping(value = "/reviewSub")
    public String reviewSub(Model model) {
        List<ReviewDto> reviewDtoList = reviewService.findReviews();
        model.addAttribute("reviews", reviewDtoList);

        return "review/reviewSub";
    }

    @PostMapping(value = "/create")
    public String reviewCreate(ReviewDto reviewDto) {
        reviewDto.setReviewCreatedTime(LocalDateTime.now());
        reviewService.save(reviewDto);
        System.out.println("createTime = " + reviewDto.getReviewCreatedTime());
        return "review/myReviewList";
    }

//    리뷰 작성
//    @PostMapping(value = "/create")
//    public String reviewCreate(ReviewDto reviewDto, Model model) {
//        reviewService.save(reviewDto);
//
//        List<ReviewDto> reviewDtoList = reviewService.findReviews();
//        model.addAttribute("reviews", reviewDtoList);
//        System.out.println("err");
//        System.out.println(reviewDtoList);
//        return "redirect:/review/reviewSub";
//    }

//    게시글 디테일 보여주기
    @GetMapping(value = "/{id}")
    public String findById(@PathVariable Long id, Model model) {
        /*
            해당 게시글의 조회수를 하나 올리고
            게시글 데이터를 가져와서 reviewShow.html에 출력
        */
        reviewService.updateHits(id);
        ReviewDto reviewDto = reviewService.findById(id);

        List<CommentDTO> commentDTOList = commentService.findAll(id);

        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("review", reviewDto);
//        model.addAttribute("page", pageable.getPageNumber());

        model.addAttribute("reviewShow", reviewDto);

        return "review/reviewShow";
    }
//    @GetMapping(value = "/reviewShow/{reviewId}")
//    public String reviewShow(@PathVariable("reviewId") Long reviewId, Model model) {
////        Review reviewEntityShow = reviewRepository.findById(id).orElse(null);
////        model.addAttribute("reviewShow", reviewEntityShow);
//        ReviewDto reviewDto = reviewService.getItemDtl(reviewId);
//        model.addAttribute("reviewShow", reviewDto);
//        return "review/reviewShow";
//    }


    //    게시글 수정하기
    @GetMapping(value = "/{id}/edit")
    public String reviewEdit(@PathVariable Long id, Model model) {
//        ReviewEntity reviewEntityEdit = reviewRepository.findById(id).orElse(null);
//        model.addAttribute("reviewEdit", reviewEntityEdit);
        ReviewDto reviewDto = reviewService.findById(id);
        model.addAttribute("reviewEdit", reviewDto);
        return "review/editReview";
    }



//    게시글 업데이트
    @PostMapping(value = "/update")
    public String update(@ModelAttribute ReviewDto reviewDto, Model model) {
//        ReviewEntity reviewEntity = form.toEntity();
//        ReviewEntity target = reviewRepository.findById(reviewEntity.getId()).orElse(null);
//
////        2-2. 기존 데이터가 있다면, 값을 갱신
//        if(target != null) {
//            reviewRepository.save(reviewEntity);
//        }
//        return "redirect:/reviewShow/" + reviewEntity.getId();

        ReviewDto review = reviewService.update(reviewDto);
        model.addAttribute("reviewShow", review);
        return "redirect:/review/" + review.getId();
    }








    @GetMapping(value = "/newReview")
    public String newReviewForm(Model model) {
//        model.addAttribute("reviewFormDto", new ReviewDto());
        return "review/newReview";
    }




    //    임시


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

