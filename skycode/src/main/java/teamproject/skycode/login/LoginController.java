package teamproject.skycode.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

        @GetMapping(value = "/login")
        public String loginIn(){
            return "login/loginIn";
        }

        @GetMapping(value = "/login/loginForm")
        public String loginForm(){
            return "/login/loginForm";
        }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        // post로 넘어온 input 데이터(name)는 매개변수로 입력한
        // MemberForm에 있는 name에 자동으로 setName이 됨
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }
    }



