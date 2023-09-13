package teamproject.skycode.myPage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import teamproject.skycode.login.MemberEntity;
import teamproject.skycode.login.MemberFormDto;
import teamproject.skycode.login.MemberRepository;
import teamproject.skycode.login.MemberService;
import teamproject.skycode.myPage.users.MemberEditFormDto;

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
            MemberEditFormDto memberEditFormDto = memberService.getMemberDtl(userInfo.getId());
            model.addAttribute("memberEditFormDto", memberEditFormDto);
            System.err.println(memberEditFormDto.getGender());
        }
        return "myPage/users/edit";
    }

    @PostMapping(value = "/user/update") // 이벤트 수정
    public String userUpdate(@Valid MemberEditFormDto memberEditFormDto, BindingResult bindingResult,
                             @RequestParam("userImgFile") MultipartFile userImgFile,
                             Principal principal, Model model) {

        if (bindingResult.hasErrors()) {
            return "myPage/users/edit";
        }
        try {
            if (principal.getName().equals(memberEditFormDto.getEmail())) {
                memberService.updateUser(memberEditFormDto, userImgFile);
                model.addAttribute("successMessage", "회원 정보 수정이 완료되었습니다");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "회원 정보 수정 중 에러가 발생하였습니다");
            return "myPage/users/edit";
        }
        return "myPage/users/edit";
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



}
