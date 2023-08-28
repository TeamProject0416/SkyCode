package teamproject.skycode.news.notion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class notionController {

    @GetMapping(value = "/news/notionUp")
    public String newsNotionUp(){
        return "news/notion/notionUp";
    }
    @GetMapping(value = "/news/notion")
    public String newsNotion(){
        return "news/notion/notion";
    }

    @GetMapping(value = "/news/notionSub")
    public String newsNotionSub(){
        return "news/notion/notionSub";
    }


}
