package teamproject.skycode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@RequestMapping(value = "/main")
public class mainController {

    @GetMapping(value = "/")

    public String skyCode() {
        return "main";
    }
}
