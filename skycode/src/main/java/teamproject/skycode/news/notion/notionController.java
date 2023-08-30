package teamproject.skycode.news.notion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor

public class notionController {

    private final NotionRepository notionRepository;

    @GetMapping(value = "/notionUp")
    public String newsNotionUp(){
        return "news/notion/notionUp";
    }
    @PostMapping(value = "notionUp/create")
    public String createNotion(NotionForm form){
        Notion notion = form.toEntity();
        Notion saved = notionRepository.save(notion);
        return "redirect:/notion";
    }

    @GetMapping(value = "/notion")
    public String newsNotion(){
        return "news/notion/notion";
    }

    @GetMapping(value = "/notionSub")
    public String newsNotionSub(){
        return "news/notion/notionSub";
    }


}
