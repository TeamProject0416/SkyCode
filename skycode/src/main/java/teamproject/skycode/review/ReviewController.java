package teamproject.skycode.review;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teamproject.skycode.event.EventFormDto;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

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
        model.addAttribute("reviewFormDto", new ReviewDto());
        return "review/newReview";
    }

    //  리뷰 생성 (Post)
    @PostMapping(value = "/create")
    public String createReview(@Valid ReviewDto reviewDto, BindingResult bindingResult, Model model,
                               @RequestParam("reviewImgFile1") MultipartFile reviewImgFile1,
                               @RequestParam("reviewImgFile2") MultipartFile reviewImgFile2) {
        if (bindingResult.hasErrors()) {
            return "review/newReview";
        }
        try {
            reviewService.saveReview(reviewDto, reviewImgFile1, reviewImgFile2);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "이벤트 등록 중 에러가 발생하였습니다");
            return "review/newReview";
        }
        return "redirect:/review/reviewSub";
    }


//    리뷰 리스트 보기
    @GetMapping(value = "/reviewSub")
    public String reviewSub(Model model) {
//        List<ReviewDto> reviewDtoList = reviewService.findAll();
        List<ReviewEntity> reviewEntityList = reviewRepository.findByAllEntity();
        model.addAttribute("reviews", reviewEntityList);
        return "review/reviewSub";
    }

//    리뷰 디테일 보여주기
    @GetMapping(value = "/{reviewId}")
    public String reviewDetail(@PathVariable("reviewId") Long reviewId, Model model) {
        /*
            해당 게시글의 조회수를 하나 올리고
            게시글 데이터를 가져와서 reviewShow.html에 출력
        */
        reviewService.updateHits(reviewId);

        ReviewDto reviewDto = reviewService.getReviewDtl(reviewId);

        List<CommentDTO> commentDTOList = commentService.findAll(reviewId);

        model.addAttribute("commentList", commentDTOList);
//        model.addAttribute("review", reviewDto);
//        model.addAttribute("page", pageable.getPageNumber());

        model.addAttribute("reviewDto", reviewDto);

        return "review/reviewShow";
    }



    //    리뷰 수정하기
    @GetMapping(value = "/{reviewId}/edit")
    public String reviewEdit(@PathVariable("reviewId") Long reviewId, Model model) {
        ReviewDto reviewDto = reviewService.getReviewDtl(reviewId);
        model.addAttribute("reviewFormDto", reviewDto);
        return "review/newReview";
    }



//    리뷰 업데이트
    @PostMapping(value = "/update")
    public String reviewUpdate(@Valid ReviewDto reviewDto, BindingResult bindingResult,
                         @RequestParam("eventImgFile1") MultipartFile reviewImgFile1,
                         @RequestParam("eventImgFile2") MultipartFile reviewImgFile2,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "review/newReview";
        }
        try {
            reviewService.updateReview(reviewDto,reviewImgFile1,reviewImgFile2);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "이벤트 등록 중 에러가 발생하였습니다");
            return "review/newReview";
        }
        return "redirect:/review/reviewSub";
    }

//    리뷰 삭제
    @GetMapping("/{reviewId}/delete")
    public String deleteEvent(@PathVariable("reviewId") Long reviewId) {
        // 리뷰 삭제 로직을 구현
        reviewService.deleteReview(reviewId);

        // 삭제 후 리다이렉션할 URL을 반환
        return "redirect:/review/reviewSub";
    }






}

