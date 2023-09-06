package teamproject.skycode.coupon;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import teamproject.skycode.event.EventFormDto;

@Controller
public class CouponController {

    @GetMapping(value = "/coupon/list") // 쿠폰 목록 보이기
    public String couponList(Model model){
        return "coupon/couponList";
    }

    @GetMapping(value = "/coupon/new") // 쿠폰 만들기
    public String couponNew(Model model){
        model.addAttribute("couponFormDto", new CouponFormDto());
        return "coupon/couponForm";
    }
}
