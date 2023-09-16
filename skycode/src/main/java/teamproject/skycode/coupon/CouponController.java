package teamproject.skycode.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teamproject.skycode.login.MemberEntity;
import teamproject.skycode.login.MemberRepository;
import teamproject.skycode.review.ReviewEntity;
import teamproject.skycode.review.ReviewRepository;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;


    @GetMapping(value = "/list") // 진행중인 쿠폰 목록 보이기
    public String couponList(Model model, Principal principal) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum",reviewNum);
        }

        List<CouponEntity> coupons = couponRepository.findByONGOING();
        model.addAttribute("coupons", coupons);
        return "coupon/couponList";
    }

    @GetMapping(value = "/end") // 종료된 쿠폰 목록 보이기
    public String couponEnd(Model model, Principal principal) {

        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum",reviewNum);
        }

        List<CouponEntity> coupons = couponRepository.findByEND();
        model.addAttribute("coupons", coupons);
        return "coupon/couponEnd";
    }

    @GetMapping(value = "/new") // 쿠폰 만들기
    public String couponNew(Model model, Principal principal) {

        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum",reviewNum);
        }

        model.addAttribute("couponFormDto", new CouponFormDto());
        return "coupon/couponForm";
    }

    @PostMapping(value = "/new") // 쿠폰 등록
    public String newCoupon(@Valid CouponFormDto couponFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {

            return "coupon/couponForm";
        }
        try {
            couponService.saveCoupon(couponFormDto);
        } catch (Exception e) {

            model.addAttribute("errorMessage", "쿠폰 등록 중 에러가 발생하였습니다");
            return "coupon/couponForm";
        }
        return "redirect:/coupon/list";
    }

    @GetMapping(value = "/{couponId}/edit") // 쿠폰 수정폼
    public String couponEdit(@PathVariable("couponId") Long couponId,
                             Principal principal, Model model) {

        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum",reviewNum);
        }

        CouponFormDto couponFormDto = couponService.getCouponDtl(couponId);
        model.addAttribute("couponFormDto", couponFormDto);
        return "coupon/couponForm";
    }

    @PostMapping(value = "/update") // 쿠폰 수정
    public String couponUpdate(@Valid CouponFormDto couponFormDto, BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "coupon/couponForm";
        }
        try {
            couponService.updateCoupon(couponFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "쿠폰 등록 중 에러가 발생하였습니다");
            return "coupon/couponForm";
        }
        return "redirect:/coupon/list";
    }

    @GetMapping("/{couponId}/delete") // 쿠폰 삭제
    public String deleteCoupon(@PathVariable("couponId") Long couponId) {
        // 쿠폰 삭제 로직을 구현
        couponService.deleteCoupon(couponId);

        // 삭제 후 리다이렉션할 URL을 반환
        return "redirect:/coupon/list";
    }

}
