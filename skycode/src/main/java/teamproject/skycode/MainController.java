package teamproject.skycode;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
//@RequestMapping(value = "/main")
public class MainController {
    @GetMapping(value = "/")
    public String skyCode(Model model, Principal principal) {
        String user = "";
        if (principal != null) {
            user = principal.getName();
        }
        System.out.println("user: " + user);
        model.addAttribute("user", user);
        return "main";
    }
}
