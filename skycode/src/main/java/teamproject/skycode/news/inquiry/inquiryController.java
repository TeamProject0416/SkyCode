package teamproject.skycode.news.inquiry;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor
public class inquiryController {

    private final InquiryRepository inquiryRepository;

    @GetMapping(value = "/inquiry")
    public String newsInquiry(){
        return "news/inquiry/inquiry";
    }

    @PostMapping(value = "/inquiry/create")
    public String newInquiryPost(InquiryForm inquiryForm) {
        Inquiry inquiry = inquiryForm.toEntity();
        Inquiry savedInquiry = inquiryRepository.save(inquiry);

        return "redirect:news/inquiryList/" + savedInquiry.getId();
    }
    @GetMapping(value = "/inquiry/{id}")
    public String userInquiry(@PathVariable Long id, Model model){
        Inquiry inquiryEntity = inquiryRepository.findById(id).orElse(null);
        inquiryEntity.setRegTime(LocalDateTime.now());
        model.addAttribute("inquirys", inquiryEntity);
        return "news/inquiry/inquiryShow";
    }

    @GetMapping(value = "/inquiryList")
    public String newsInquiryList(){
        return "news/inquiry/inquiryList";
    }

    @GetMapping(value = "/inquiryShow")
    public String newsInquiryShow(){
        return "news/inquiry/inquiryShow";
    }

}




