package teamproject.skycode.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Member;

@Controller
public class LoginController {


    @GetMapping(value = "/login")
    public String loginIn() {
        return "login/loginIn";
    }

    @GetMapping(value = "/login/loginForm")
    public String loginForm() {
        return "/login/loginForm";
    }


}

