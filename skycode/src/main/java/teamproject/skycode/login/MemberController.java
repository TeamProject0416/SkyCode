package teamproject.skycode.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import teamproject.skycode.event.EventEntity;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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


    @GetMapping(value = "/new")
    public String memberForm(Model model, Principal principal) {

        model.addAttribute("memberFormDto", new MemberFormDto());

        // 유저 로그인
        String user = "";
        if (principal != null) {
            user = principal.getName();
        }
        model.addAttribute("user", user);

        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String memberSave(@Valid MemberFormDto memberFormDto,
                             BindingResult bindingResult, Model model) {
        System.err.println(memberFormDto.getGender());
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.err.println(error.getDefaultMessage());
            }
            return "member/memberForm";
        }
        try {
            MemberEntity member = MemberEntity.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }
        return "redirect:/";

    }

    @GetMapping(value = "/login")
    public String loginMember(Principal principal, Model model) {
        // 유저 로그인
        String user = "";
        if (principal != null) {
            user = principal.getName();
        }
        model.addAttribute("user", user);

        return "/member/memberLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }

    @GetMapping(value = "list")
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










