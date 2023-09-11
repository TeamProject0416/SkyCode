package teamproject.skycode.news.faq;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import teamproject.skycode.news.notion.Notion;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/news")
public class FaqController {

    @Autowired
    private FaqRepository faqRepository;

    @Autowired
    private FaqService faqService;


    @GetMapping(value = "/faqUp")
    public String newsFaqUp(Model model){
        model.addAttribute("faqForm", new FaqForm());
        System.out.println("1234");
        return "news/faq/faqUp";
    }

    @PostMapping(value = "/faq/faq")
    public String createFaq(@ModelAttribute FaqForm faqForm){
        Faq faq = faqForm.toEntity();
        faqRepository.save(faq);
        System.out.println("faq보내기");
        return "redirect:/news/faq/faq";
    }
    private static final int PAGE_SIZE = 10; // You can change this to your desired page size


    @GetMapping("/faq/faq")
    public String faqPage(
            @RequestParam(name = "page", defaultValue = "1") int page,
            Model model
    ) {
        Page<Faq> faqPage = faqRepository.findAll(PageRequest.of(page - 1, PAGE_SIZE));
        int totalPages = faqPage.getTotalPages();

        // Create a list of page numbers for the pager
        java.util.List<Integer> pageNumbers = new java.util.ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pageNumbers.add(i);
        }

        model.addAttribute("faqs", faqPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageNumbers", pageNumbers);

        return "news/faq/faq";
    }
//    @GetMapping(value = "/faq/faq")
//    public String newsFaq(Model model){
//        List<Faq> faqs = faqService.getAllFaqs();
//        model.addAttribute("faqs", faqs);
//        return "news/faq/faq";
//    }



}
