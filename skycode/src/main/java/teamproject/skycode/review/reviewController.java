package teamproject.skycode.review;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class reviewController {
    @GetMapping(value = "/reviewSub")
    public String reviewSub(){
        return "review/reviewSub";
    }
}

