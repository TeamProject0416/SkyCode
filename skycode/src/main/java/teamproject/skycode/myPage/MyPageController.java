package teamproject.skycode.myPage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageController {

    @GetMapping(value = "user_shopping/orderList")
    public String userOrderList(){
        return "myPage/shopping/orderList";
    }

    @GetMapping(value = "user_shopping/point")
    public String userPoint(){
        return "myPage/shopping/point";
    }

    @GetMapping(value = "user_shopping/coupon")
    public String userCoupon(){
        return "myPage/shopping/coupon";
    }
}
