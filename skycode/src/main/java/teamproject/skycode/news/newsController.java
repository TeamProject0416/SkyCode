package teamproject.skycode.news;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class newsController {

    @GetMapping(value = "/news/faq")
    public String newsFaq(){
        return "news/faq";
    }

    @GetMapping(value = "/news/notion")
    public String newsNotion(){
        return "news/notion";
    }

    @GetMapping(value = "/news/notionSub")
    public String newsNotionSub(){
        return "news/notionSub";
    }

    @GetMapping(value = "/inquiry")
    public String newsInquiry(){
        return "news/inquiry";
    }
}
