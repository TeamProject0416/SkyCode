package teamproject.skycode.coupon;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CouponController {

    @GetMapping(value = "/coupon")
    public String coupon(Model model){
        return "coupon/couponForm";
    }
}
