package teamproject.skycode.news.notion;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDateTime;

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

    @Autowired
    private NotionViewCountRepository notionViewCountRepository;


    @Autowired
    public void NotionController(NotionRepository notionRepository) {
        this.notionRepository = notionRepository;
    }


    // 공지사항 등록 화면
    @GetMapping(value = "/notionUp")
    public String newsNotionUp(Model model){
        model.addAttribute("notionForm", new NotionForm());
        return "news/notion/notionUp";
    }


    @PostMapping(value = "notionUp/create")
    public String createNotion(@ModelAttribute NotionForm form){
        Notion notion = form.toEntity();
        System.out.println("1234");
        notionRepository.save(notion);
        return "redirect:/news/notion/notion";
    }

    // 공지사항 등록시 전송하는
//    @PostMapping(value = "notionUp/create")
//    public String createNotion(@ModelAttribute NotionForm form){
//        Notion notion = form.toEntity();
//        System.out.println("1234");
//        notionRepository.save(notion);
//        return "redirect:/news/notion/notion";
//    }

    // 공지사항 리스트 출력
    @GetMapping("/notion/notion")
    public String notionList(Model model, @RequestParam(defaultValue = "0") int page, String sortBy){
        int pageSize = 4;
        Page<Notion> notionPage = notionRepository.findAll(PageRequest.of(page, pageSize));

        List<Notion> notions = notionRepository.findAllOrderNotionByRegistrationTimeDesc();

        long totalNotionCount = notionService.getTotalNotionCount();
        model.addAttribute("totalNotionCount", totalNotionCount);
        if ("popularity".equals(sortBy)) {
            notions = notionService.getAllNotionsSortedByPopularity();
            System.out.println("될까");
        } else {
            // 날짜 내림차순으로 정렬
            // Default to sorting by date (most recent first)
            notions = notionService.getAllNotionsSortedByDate();
        }
        System.out.println("된다");

        model.addAttribute("notions", notions);
        model.addAttribute("notions", notionPage);


        return "/news/notion/notion";
    }

    @PostMapping("/notion/notion")
    public String submitNotion(@ModelAttribute NotionForm notionForm, Model model) {
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
//            NotionViewCount viewCount = notionViewCountService.incrementViewCount(id);
            notion.setCountView(notion.getCountView() + 1); // Increment the view count
            notionRepository.save(notion);

            model.addAttribute("notion", notion);
            model.addAttribute("viewCount", notion.getCountView());
            System.out.println("좀");
            return "news/notion/notionSub"; // Adjust the view name
        }
        System.out.println("제발");

        // Handle case when inquiry is not found
        return "error"; // Change to the appropriate view name
    }

    @PostMapping("/notion/delete")
    public String deleteNotion(@RequestParam Long notionId){
        notionService.deleteNotion(notionId);
        System.out.println("삭제");
        return "redirect:/news/notion/notion";
    }

    @GetMapping("/notion/edit/{notionId}")
    public String showEditForm(@PathVariable Long notionId, Model model){

        Notion notion = notionService.findById(notionId);

        NotionForm notionForm = new NotionForm();
        notionForm.setId(notion.getId());
        notionForm.setType(notion.getType());
        notionForm.setNotionTitle(notion.getNotionTitle());
        notionForm.setNotionContent(notion.getNotionContent());

        model.addAttribute("notionForm", notionForm);
        System.out.println("수정중");

        return "news/notion/notionEdit";
    }

    @PostMapping("/notionUp/edit")
    public String editNotion(@ModelAttribute("notionForm") NotionForm notionForm){

        Notion existingNotion = notionService.findById(notionForm.getId());

        // 기존 Notion의 필드를 업데이트합니다.
        existingNotion.setType(notionForm.getType());
        existingNotion.setNotionTitle(notionForm.getNotionTitle());
        existingNotion.setNotionContent(notionForm.getNotionContent());
        existingNotion.setRegTime(LocalDateTime.now());

        // 수정된 Notion을 저장합니다.
        notionService.editNotion(existingNotion);

        return "redirect:/news/notion/notionSub/" + existingNotion.getId();
    }

}