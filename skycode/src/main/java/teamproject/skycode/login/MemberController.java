package teamproject.skycode.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import teamproject.skycode.review.ReviewEntity;
import teamproject.skycode.review.ReviewRepository;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.List;

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
public class MemberController {

    // 생성자 주입
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    // 메세지
    private String encodeMessage(String message) {
        try {
            return URLEncoder.encode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            String errorMessage = "지원되지 않는 문자 인코딩: " + e.getMessage();// 예외 처리
            System.err.println(errorMessage);
            return "redirect:/";
        }
    }

    @GetMapping(value = "/new")
    public String memberForm(Model model, Principal principal) {

        model.addAttribute("memberFormDto", new MemberFormDto());

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum",reviewNum);
        }

        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String memberSave(@Valid MemberFormDto memberFormDto,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.err.println(error.getDefaultMessage());
            }
            return "member/memberForm";
        }
        try {
            MemberEntity member = MemberEntity.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
            String message = "회원가입 되었습니다";
            return "redirect:/member/login?message=" + encodeMessage(message);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }
    }

    @GetMapping(value = "/login")
    public String loginMember(@RequestParam(name = "logout", required = false) String logout,
                              @RequestParam(name = "message", required = false) String message,
                              Principal principal, Model model) {
        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum",reviewNum);
        }

        if (logout != null) {
            model.addAttribute("logoutMessage", logout);
        }
        if (message != null) {
            model.addAttribute("successMessage", message);
        }

        return "/member/memberLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model, Principal principal) {

        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum",reviewNum);
        }

        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }


    //---------------------------/admin------------------------//
    @GetMapping(value = "/list")
    public String memberList(Model model) {
        List<MemberEntity> memberList = memberRepository.findAll();
        model.addAttribute("memberList", memberList);
        return "/member/list";
    }

    @GetMapping("/delete/{memberId}")
    public String memberDelete(@PathVariable("memberId") Long memberId) {
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(EntityNotFoundException::new);
        memberRepository.delete(member);
        return "redirect:/member/list";
    }


}
