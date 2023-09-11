package teamproject.skycode.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
public class MemberController {

    // 생성자 주입
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;



    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }
    @PostMapping(value = "/new")
    public String memberSave(@Valid MemberFormDto memberFormDto,
                             BindingResult bindingResult, Model model ){
        if(bindingResult.hasErrors()){
            return "member/memberForm";
        }
        try {
            MemberEntity member = MemberEntity.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        }catch(IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "member/memberForm";
        }
        return "redirect:/";

    }

    @GetMapping(value = "/login")
    public String loginMember(){
        return "/member/memberLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }

}










