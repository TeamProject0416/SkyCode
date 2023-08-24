package teamproject.skycode.news;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class newsController {

    @GetMapping(value = "/faq")
    public String newsFaq(){
        return "news/faq";
    }

    @GetMapping(value = "/notion")
    public String newsNotion(){
        return "news/notion";
    }

    @GetMapping(value = "/notionSub")
    public String newsNotionSub(){
        return "news/notionSub";
    }

    @GetMapping(value = "/inquiry")
    public String newsInquiry(){
        return "news/inquiry";
    }
}
