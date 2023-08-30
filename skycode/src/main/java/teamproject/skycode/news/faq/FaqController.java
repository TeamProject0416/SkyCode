package teamproject.skycode.news.faq;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/news")
public class FaqController {

    private final FaqRepository faqRepository;

    @GetMapping(value = "/faqUp")
    public String newsFaqUp(){
        return "news/faq/faqUp";
    }
    @PostMapping(value = "/faq/create")
    public String createFaq(FaqForm form){
        Faq faq = form.toEntity();
        Faq saved = faqRepository.save(faq);

        return "redirect:/faq";
    }

    @GetMapping(value = "/faq")
    public String newsFaq(){
        return "news/faq/faq";
    }

}
