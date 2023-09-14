package teamproject.skycode.myPage;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import teamproject.skycode.login.MemberEntity;
import teamproject.skycode.login.MemberRepository;
import teamproject.skycode.login.MemberService;
import teamproject.skycode.myPage.users.MemberEditFormDto;
import teamproject.skycode.myPage.users.PasswordFormDto;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    //---------------------------/user------------------------//
    @GetMapping(value = "user") // 유저 모두 보기
    public String user() {
        return "myPage/users/user";
    }


    //---------------------------/user/edit------------------------//
    @GetMapping(value = "/user/edit") // 회원 정보 폼
    public String userEdit(Model model, Principal principal) {
        // 유저 로그인
        String user = "";
        if (principal != null) {
            user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            MemberEditFormDto memberEditFormDto = memberService.getMemberDtl(userInfo.getId());
            model.addAttribute("memberEditFormDto", memberEditFormDto);
        }
        return "myPage/users/edit";
    }

    @PostMapping(value = "/user/update") // 회원 정보 수정
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

    @GetMapping("/user/delete/{memberId}") // 회원 삭제
    public String userDelete(@PathVariable("memberId") Long memberId,
                             Principal principal, Model model) {
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(EntityNotFoundException::new);
        model.addAttribute("passwordFormDto", new PasswordFormDto());
        try {
            if (principal.getName().equals(member.getEmail())) {
                memberRepository.delete(member);

                // 강제 로그아웃
                SecurityContextHolder.clearContext();

                return "redirect:/member/login?message=" + URLEncoder.encode("탈퇴가 완료되었습니다", "UTF-8");
            }
        } catch (Exception e) {
            model.addAttribute("errorDelete", "탈퇴 중 에러가 발생하였습니다");
            return "/myPage/users/edit";
        }
        return "redirect:/";
    }


    //---------------------------/user/collections------------------------//
    @GetMapping(value = "/user/collections")
    public String userCollections() {
        return "myPage/users/collections";
    }


    //---------------------------/user/praise------------------------//
    @GetMapping(value = "/user/praise")
    public String userPraise() {
        return "myPage/users/praise";
    }


    //---------------------------/user/edit_password------------------------//
    @GetMapping(value = "/user/edit_password")
    public String userEditPw(Principal principal, Model model) {
        if (principal != null) {

        }
        return "myPage/users/edit_password";
    }


    @PostMapping("/user/edit_password/update")
    public String userEditPwUpdate(PasswordFormDto passwordFormDto, Principal principal,
                                   Model model) {
        // 현재 로그인한 사용자의 이메일 가져오기
        String userEmail = principal.getName();

        // 사용자 정보 가져오기
        MemberEntity user = memberRepository.findByEmail(userEmail);
        try {
            if (user != null && passwordEncoder.matches(passwordFormDto.getCheckPassword(), user.getPassword())) {
                // 현재 비밀번호가 일치하면 새로운 비밀번호 설정
                String newPassword = passwordEncoder.encode(passwordFormDto.getNewPassword());
                user.setPassword(newPassword);
                memberRepository.save(user);

                // 로그아웃
                SecurityContextHolder.clearContext();

                return "redirect:/member/login?message=" + URLEncoder.encode("비밀번호가 변경되었습니다.", "UTF-8");
            }
        } catch (Exception e) {
            // 현재 비밀번호가 일치하지 않을 경우 오류 메시지 표시
            model.addAttribute("errorUpdate", "비밀번호 변경 중 에러가 발생하였습니다");
            return "/myPage/users/edit_password";
        }
        return "redirect:/";
    }


    //---------------------------/user_shopping/orderList------------------------//
    @GetMapping(value = "/user_shopping/orderList")
    public String userOrderList() {
        return "myPage/shopping/orderList";
    }


    //---------------------------/user_shopping/cart------------------------//
    @GetMapping(value = "/user_shopping/cart")
    public String userCart() {
        return "myPage/shopping/cart";
    }


    //---------------------------/user_shopping/point------------------------//
    @GetMapping(value = "/user_shopping/point")
    public String userPoint() {
        return "myPage/shopping/point";
    }


    //---------------------------/user_shopping/coupon------------------------//
    @GetMapping(value = "/user_shopping/coupon")
    public String userCoupon() {
        return "myPage/shopping/coupon";
    }


    //---------------------------/user_shopping/questions------------------------//
    @GetMapping(value = "/user_shopping/questions")
    public String userQuestions() {
        return "myPage/shopping/questions";
    }


    //---------------------------/user_trip/tripTool------------------------//
    @GetMapping(value = "/user_trip/tripTool")
    public String userTripTool() {
        return "myPage/trip/tripTool";
    }


    //---------------------------/user_review------------------------//
    @GetMapping(value = "/user_review")
    public String userReview() {
        return "myPage/review/review";
    }

}
