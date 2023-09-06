package teamproject.skycode.news.notion;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor

public class NotionController {

    @Autowired
    private NotionRepository notionRepository;

    @Autowired
    private NotionService notionService;

    @Autowired
    private NotionViewCountService notionViewCountService;

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

    @PostMapping("/notion/notion")
    public String submitInquiry(@ModelAttribute NotionForm notionForm, Model model) {
        Notion savedNotion = notionService.saveNotion(notionForm); // Save or update inquiry
        System.out.println("제발1");
        if (savedNotion != null) {
            Long id = savedNotion.getId(); // Get the id of the saved/updated inquiry

            // Now, based on the id, determine the URL to redirect to
            String redirectUrl = "redirect:/news/notion/notionSub" + id; // Adjust the URL pattern according to your mapping
            System.out.println("제발2");

            return redirectUrl;
        }

        // Handle error case if savedInquiry is null
        // You can return an error view or redirect to an error page
        return "error"; // Change to the appropriate view name

    }


    @GetMapping("/notion/notionSub/{id}")
    public String showNotionById(@PathVariable Long id, Model model) {
        Notion notion = notionService.getNotionById(id);

        if (notion != null) {
            NotionViewCount viewCount = notionViewCountService.notionViewCount(id);

            model.addAttribute("notion", notion);
            model.addAttribute("viewCount", viewCount.getCount());
            System.out.println("좀");
            return "news/notion/notionSub"; // Adjust the view name
        }
        System.out.println("제발");

        // Handle case when inquiry is not found
        return "error"; // Change to the appropriate view name
    }


}
