package teamproject.skycode.login;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


//    로그인창
    @GetMapping(value = "/login")
    public String loginIn() {
        return "/member/memberLoginForm";
    }


//    회원가입창
    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        System.out.println("1234");
        return "/member/memberForm";
    }

    @PostMapping(value = "/login/new")
    public String memberForm2(@Valid MemberFormDto memberFormDto,
                              BindingResult bindingResult, Model model ){
        System.out.println("2345");
        if(bindingResult.hasErrors()){
            return "/member/memberForm";
        }
        try {
            Member member = Member.createMember(memberFormDto);
            memberService.saveMember(member);
        }catch(IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "/member/memberForm";
        }
        System.out.println("3456");
        return "redirect:/";

    }


// 로그인 창에서 오류창
    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해 주세요");
        return "/member/memberLoginForm";
    }
}





