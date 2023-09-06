package teamproject.skycode.news.inquiry;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/news")
public class inquiryController {

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private InquiryViewCountRepository inquiryViewCountRepository;

    @Autowired
    private InquiryService inquiryService;

    @Autowired
    private InquiryViewCountService inquiryViewCountService;

    @GetMapping("/inquiry")
    public String showInquiryForm(Model model) {
        model.addAttribute("inquiryForm", new InquiryForm());
        return "news/inquiry/inquiry";
    }

    @PostMapping("/inquiry/create")
    public String submitInquiry(@ModelAttribute InquiryForm inquiryForm) {
        // InquiryForm을 Inquiry 엔티티로 변환하여 저장
        System.out.println("1234");
         Inquiry inquiry = inquiryForm.toEntity(); // InquiryForm에서 Inquiry 엔티티로 변환하는 메서드 필요
//        Inquiry savedInquiry = inquiryService.saveInquiry(inquiryForm);
        inquiryRepository.save(inquiry);

        return "redirect:/news/inquiry/inquiryList"; // 저장 후 다시 폼 페이지로 리다이렉트
    }

    @GetMapping("/inquiry/inquiryList")
    public String inquiryList(Model model) {
        List<Inquiry> inquiries = inquiryService.getAllInquiries();
        long totalInquiryCount = inquiryService.getTotalInquiryCount();

        model.addAttribute("inquiries", inquiries);
        model.addAttribute("totalInquiryCount", totalInquiryCount);

        System.out.println("4");
        System.out.println("432");

        // 조회된 데이터가 없을 경우 예외 처리 등을 수행
        return "news/inquiry/inquiryList"; // 혹은 다른 페이지로 이동
    }

    @PostMapping("inquiry/inquiryShow")
    public String submitInquiry(@ModelAttribute InquiryForm inquiryForm, Model model) {
        Inquiry savedInquiry = inquiryService.saveInquiry(inquiryForm); // Save or update inquiry
        System.out.println("제발1");
        if (savedInquiry != null) {
            Long id = savedInquiry.getId(); // Get the id of the saved/updated inquiry

            // Now, based on the id, determine the URL to redirect to
            String redirectUrl = "redirect:/news/inquiry/show/" + id; // Adjust the URL pattern according to your mapping
            System.out.println("제발2");

            return redirectUrl;
        }

        // Handle error case if savedInquiry is null
        // You can return an error view or redirect to an error page
        return "error"; // Change to the appropriate view name

    }

//    @GetMapping("/inquiry/show/{id}")
//    public String showInquiryById(@PathVariable Long id, Model model) {
//        Inquiry inquiry = inquiryService.getInquiryById(id);
//
//        if (inquiry != null) {
//            model.addAttribute("inquiry", inquiry);
//            System.out.println("좀");
//            return "news/inquiry/inquiryShow"; // Adjust the view name
//        }
//        System.out.println("제발");
//
//        // Handle case when inquiry is not found
//        return "error"; // Change to the appropriate view name
//    }

    @GetMapping("/inquiry/show/{id}")
    public String showInquiryById(@PathVariable Long id, Model model) {
        Inquiry inquiry = inquiryService.getInquiryById(id);

        if (inquiry != null) {
            InquiryViewCount viewCount = inquiryViewCountService.incrementViewCount(id);

            model.addAttribute("inquiry", inquiry);
            model.addAttribute("viewCount", viewCount.getCount());

            return "news/inquiry/inquiryShow"; // Adjust the view name
        }else {
            // Handle case when inquiry is not found
            return "error"; // Change to the appropriate view name
        }
    }


    @GetMapping("/news/inquiry/search")
    public String searchInquiries(@RequestParam("search-type") String searchType,
                                  @RequestParam("search-value") String searchValue,
                                  Model model) {
        List<Inquiry> searchResults = inquiryService.searchInquiries(searchType, searchValue);
        model.addAttribute("inquiries", searchResults);
        System.out.println("대박");
        return "news/inquiry/inquiryList"; // This should be the name of your Thymeleaf template
    }

}








