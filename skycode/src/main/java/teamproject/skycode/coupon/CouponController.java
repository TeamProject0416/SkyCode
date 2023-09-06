package teamproject.skycode.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import teamproject.skycode.event.EventEntity;
import teamproject.skycode.event.EventFormDto;

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
}
