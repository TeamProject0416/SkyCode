package teamproject.skycode.news.faq;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping(value = "/faq/create")
    public String createFaq(@ModelAttribute FaqForm faqForm){
        Faq faq = faqForm.toEntity();
        faqRepository.save(faq);
        return "news/faq/faq";
    }

    @GetMapping(value = "/faq/faq")
    public String newsFaq(Model model){
        List<Faq> faqs = faqService.getAllFaqs();
        model.addAttribute("faqs", faqs);
        return "news/faq/faq";
    }



}
