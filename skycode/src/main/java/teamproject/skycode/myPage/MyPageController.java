package teamproject.skycode.myPage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import teamproject.skycode.login.Member;
import teamproject.skycode.login.MemberForm;
import teamproject.skycode.login.MemberRepository;
import teamproject.skycode.login.MemberService;

import javax.persistence.EntityNotFoundException;

@Controller
@RequiredArgsConstructor
public class MyPageController {


    @GetMapping(value = "user")
    public String user(){
        return "myPage/users/user";
    }

    @GetMapping(value = "/user/edit")
    public String userEdit(@PathVariable("userId") Long userId, Model model){
        return "myPage/users/edit";
    }

    @GetMapping(value = "user/collections")
    public String userCollections(){
        return "myPage/users/collections";
    }

    @GetMapping(value = "user/praise")
    public String userPraise(){
        return "myPage/users/praise";
    }

    @GetMapping(value = "/user/edit_password")
    public String userEditPw(){
        return "myPage/users/edit_password";
    }




    @GetMapping(value = "user_shopping/orderList")
    public String userOrderList(){
        return "myPage/shopping/orderList";
    }

    @GetMapping(value = "user_shopping/cart")
    public String userCart(){
        return "myPage/shopping/cart";
    }

    @GetMapping(value = "user_shopping/point")
    public String userPoint(){
        return "myPage/shopping/point";
    }

    @GetMapping(value = "user_shopping/coupon")
    public String userCoupon(){
        return "myPage/shopping/coupon";
    }

    @GetMapping(value = "user_shopping/questions")
    public String userQuestions(){
        return "myPage/shopping/questions";
    }

    @GetMapping(value = "user_trip/tripTool")
    public String userTripTool(){
        return "myPage/trip/tripTool";
    }

    @GetMapping(value = "/user_review")
    public String userReview(){
        return "myPage/review/review";
    }

    @GetMapping(value = "/user_review/write")
    public String userReview_write(){
        return "myPage/review/review_write";
    }

}
