package teamproject.skycode.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

<<<<<<< Updated upstream
import javax.servlet.http.HttpSession;
=======
import javax.persistence.EntityNotFoundException;
>>>>>>> Stashed changes
import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
public class MemberController {

    // 생성자 주입
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

<<<<<<< Updated upstream

=======
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
>>>>>>> Stashed changes

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










