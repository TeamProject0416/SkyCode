package teamproject.skycode.myPage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import teamproject.skycode.event.EventFormDto;
import teamproject.skycode.login.MemberEntity;
import teamproject.skycode.login.MemberRepository;
import teamproject.skycode.login.MemberService;
import teamproject.skycode.myPage.users.EditDto;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping(value = "user")
    public String user() {
        return "myPage/users/user";
    }


    @GetMapping(value = "/user/edit")
    public String userEdit(Model model, Principal principal) {
        // 유저 로그인
        String user = "";
        if (principal != null) {
            user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
        }
        return "myPage/users/edit";
    }

    @PostMapping(value = "/user/update") // 이벤트 수정
    public String userUpdate(@Valid EditDto editDto, BindingResult bindingResult,
                             @RequestParam("userImgFile") MultipartFile userImgFile,
                             Principal principal, Model model) {

        if (bindingResult.hasErrors()) {
            return "myPage/users/edit";
        }
        try {
            if (principal != null){
                memberService.updateUser(editDto, userImgFile);
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "이벤트 등록 중 에러가 발생하였습니다");
            return "myPage/users/edit";
        }
        return "redirect:/user/edit";
    }


    @GetMapping(value = "user/collections")
    public String userCollections() {
        return "myPage/users/collections";
    }

    @GetMapping(value = "user/praise")
    public String userPraise() {
        return "myPage/users/praise";
    }

    @GetMapping(value = "/user/edit_password")
    public String userEditPw() {
        return "myPage/users/edit_password";
    }


    @GetMapping(value = "user_shopping/orderList")
    public String userOrderList() {
        return "myPage/shopping/orderList";
    }

    @GetMapping(value = "user_shopping/cart")
    public String userCart() {
        return "myPage/shopping/cart";
    }

    @GetMapping(value = "user_shopping/point")
    public String userPoint() {
        return "myPage/shopping/point";
    }

    @GetMapping(value = "user_shopping/coupon")
    public String userCoupon() {
        return "myPage/shopping/coupon";
    }

    @GetMapping(value = "user_shopping/questions")
    public String userQuestions() {
        return "myPage/shopping/questions";
    }

    @GetMapping(value = "user_trip/tripTool")
    public String userTripTool() {
        return "myPage/trip/tripTool";
    }

    @GetMapping(value = "/user_review")
    public String userReview() {
        return "myPage/review/review";
    }

    @GetMapping(value = "/user_review/write")
    public String userReview_write() {
        return "myPage/review/review_write";
    }

}
