package teamproject.skycode.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

//        @Autowired
//        ArticleRepository articleRepository;
//
//        @Autowired
//        LoginRepository loginRepository;
//
//        @Autowired
//        MemberRepository  memberRepository;

    }

