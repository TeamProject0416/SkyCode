package teamproject.skycode.news;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class newsController {

    @GetMapping(value = "/faq")
    public String reviewSub(){
        return "news/faq";
    }
}
