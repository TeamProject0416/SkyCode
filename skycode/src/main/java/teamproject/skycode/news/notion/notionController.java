package teamproject.skycode.news.notion;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor

public class notionController {

    @Autowired
    private NotionRepository notionRepository;

    @Autowired
    private NotionService notionService;

    @GetMapping(value = "/notionUp")
    public String newsNotionUp(Model model){
        model.addAttribute("notionForm", new NotionForm());
        return "news/notion/notionUp";
    }
    @PostMapping(value = "notionUp/create")
    public String createNotion(NotionForm form){
        Notion notion = form.toEntity();
        System.out.println("1234");
        notionRepository.save(notion);
        return "redirect:/news/notion/notion";
    }

    @GetMapping("/notion/notion")
    public String notionList(Model model){
        List<Notion> notions = notionService.getAllNotions();
        System.out.println("1");

        model.addAttribute("notions", notions);

        return "/news/notion/notion";
    }


    @GetMapping(value = "/notionSub")
    public String newsNotionSub(){
        return "news/notion/notionSub";
    }


}
