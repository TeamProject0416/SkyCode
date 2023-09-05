package teamproject.skycode.review;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @GetMapping(value = "/newReview")
    public String newReviewForm(Model model) {
//        model.addAttribute("reviewFormDto", new ReviewDto());
        return "review/newReview";
    }

    //  리뷰 생성 (Post)
    @PostMapping(value = "/create")
    public String reviewCreate(ReviewDto reviewDto) throws IOException {
        reviewService.save(reviewDto);
        System.out.println("createTime = " + reviewDto.getReviewCreatedTime());
        return "redirect:/review/reviewSub";
    }


//    리뷰 리스트 보기
    @GetMapping(value = "/reviewSub")
    public String reviewSub(Model model) {
        List<ReviewDto> reviewDtoList = reviewService.findReviews();

        // 리뷰 리스트를 순회하면서 storedFileName 값을 출력
        for (ReviewDto reviewDto : reviewDtoList) {
            String storedFileName = reviewDto.getStoredFileName();
            System.out.println("Stored File Name: " + storedFileName);
        }
        // Stored File Name: /review_img/1693917474345_청바지23.jpg 저장은 잘 되지만 경로에 뭐가 더 붙어야 될까..?

        model.addAttribute("reviews", reviewDtoList);
        return "review/reviewSub";
    }

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






}

