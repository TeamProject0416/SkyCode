package teamproject.skycode.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;
    private final CouponRepository couponRepository;

    @GetMapping(value = "/list") // 쿠폰 목록 보이기
    public String couponList(Model model) {
        List<CouponEntity> coupons = couponRepository.findAll();
        model.addAttribute("coupons", coupons);
        return "coupon/couponList";
    }

    @GetMapping(value = "/new") // 쿠폰 만들기
    public String couponNew(Model model) {
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
            e.toString();
            e.printStackTrace(); // 예외 정보를 콘솔에 출력

            model.addAttribute("errorMessage", "이벤트 등록 중 에러가 발생하였습니다");
            return "coupon/couponForm";
        }
        return "redirect:/coupon/list";
    }

    @GetMapping(value = "/{couponId}/edit") // 쿠폰 수정폼
    public String couponEdit(@PathVariable("couponId") Long couponId, Model model) {
        CouponFormDto couponFormDto = couponService.getCouponDtl(couponId);
        model.addAttribute("couponFormDto", couponFormDto);
        return "coupon/couponForm";
    }

    @PostMapping(value = "/update") // 이벤트 수정
    public String couponUpdate(@Valid CouponFormDto couponFormDto, BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return "coupon/couponForm";
        }
        try {
            couponService.updateCoupon(couponFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "이벤트 등록 중 에러가 발생하였습니다");
            return "coupon/couponForm";
        }
        return "redirect:/coupon/list";
    }

    @GetMapping("/{couponId}/delete") // 이벤트 삭제
    public String deleteCoupon(@PathVariable("couponId") Long couponId) {
        // 이벤트 삭제 로직을 구현
        couponService.deleteCoupon(couponId);

        // 삭제 후 리다이렉션할 URL을 반환
        return "redirect:/coupon/list";
    }

}
