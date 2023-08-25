package teamproject.skycode.news.faq;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class faqController {

    @GetMapping(value = "/news/faqUp")
    public String newsFaqUp(){
        return "news/faq/faqUp";
    }

    @GetMapping(value = "/news/faq")
    public String newsFaq(){
        return "news/faq/faq";
    }

}
