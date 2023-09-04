package teamproject.skycode.news.inquiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

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

    @Autowired
    public void InquiryController(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @GetMapping("/inquiry")
    public String showInquiryForm(Model model) {
        model.addAttribute("inquiryForm", new InquiryForm());
        return "news/inquiry/inquiry";
    }

    @PostMapping("/inquiry/create")
    public String submitInquiry(@ModelAttribute InquiryForm inquiryForm) {
        // InquiryForm을 Inquiry 엔티티로 변환하여 저장
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
            InquiryViewCount viewCount = inquiryViewCountService.incrementViewCount(id);

            // Now, based on the id, determine the URL to redirect to
            String redirectUrl = "redirect:/news/inquiry/show/" + id; // Adjust the URL pattern according to your mapping
            System.out.println("제발2");

            model.addAttribute("viewCount", viewCount.getCount());


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

//    @GetMapping("/inquiry/show/{id}")
//    public String showInquiryById(@PathVariable Long id, Model model) {
//        Inquiry inquiry = inquiryService.getInquiryById(id);
//
//        if (inquiry != null) {
//            InquiryViewCount viewCount = inquiryViewCountService.incrementViewCount(id);
//
//            model.addAttribute("inquiry", inquiry);
//            model.addAttribute("viewCount", viewCount.getCount());
//
//            return "news/inquiry/inquiryShow"; // Adjust the view name
//        }else {
//            // Handle case when inquiry is not found
//            return "error"; // Change to the appropriate view name
//        }
//    }

    @GetMapping("/inquiry/show/{id}")
    public String showInquiryById(@PathVariable Long id, Model model) {
        Inquiry inquiry = inquiryService.getInquiryById(id);

        if (inquiry != null) {
            // Increment view count and save
            inquiry.setViewCount(inquiry.getViewCount() + 1); // Increment the view count
            inquiryRepository.save(inquiry); // Save the updated inquiry

            model.addAttribute("inquiry", inquiry);
            model.addAttribute("viewCount", inquiry.getViewCount());

            return "news/inquiry/inquiryShow";
        } else {
            // Handle inquiry not found case
            // Return appropriate error view or handle differently
            return "error";
        }
    }

    @PostMapping("/submitComment")
    public String submitComment(CommentForm commentForm) {
        // Inquiry를 조회하는 예시: 여기서는 Inquiry ID를 가져오는 방법으로 findById() 메서드를 사용합니다.
        Long id = commentForm.getPostId();
        InquiryForm inquiryForm = new InquiryForm();
        Long yourInquiryId = 1L;
        inquiryForm.setId(yourInquiryId); // yourInquiryId는 실제 Inquiry의 ID 값
        Long inquiryId = inquiryForm.getId();

        if (id != null) {
            Inquiry inquiry = inquiryRepository.findById(id).orElse(null);
            if (inquiry != null) {
                // inquiry를 사용하여 필요한 작업을 수행합니다.
                String commentText = commentForm.getComment();
                System.out.println("댓글 보내기");
            } else {
                // ID에 해당하는 Inquiry가 없는 경우에 대한 처리를 수행합니다.
            }
        } else {
            // ID가 없는 경우에 대한 처리를 수행합니다.
        }
        System.out.println("이거다");
        System.out.println(inquiryId);

        // 처리 후, 리다이렉트 또는 다른 화면으로 이동합니다.
        return "redirect:/news/inquiry/show/" + inquiryId;
    }


//    @GetMapping("/news/inquiry/search")
//    public String searchInquiries(@RequestParam("search-type") String searchType,
//                                  @RequestParam("search-value") String searchValue,
//                                  Model model) {
//        List<Inquiry> searchResults = inquiryService.searchInquiries(searchType, searchValue);
//        model.addAttribute("inquiries", searchResults);
//        System.out.println("대박");
//        return "news/inquiry/inquiryList"; // This should be the name of your Thymeleaf template
//    }

    @GetMapping("/inquiry/search")
    public String searchInquiries(
            @RequestParam("search-type") String searchType,
            @RequestParam("search-value") String searchValue,
            Model model
    ) {
        List<Inquiry> searchResults;
        System.out.println("검색");
        if ("title".equals(searchType)) {
            searchResults = inquiryService.findByInquiryTitleContaining(searchValue);
        } else if ("content".equals(searchType)) {
            searchResults = inquiryService.findByInquiryContentContaining(searchValue);
        } else if ("id".equals(searchType)) {
            searchResults = inquiryService.findByIdContaining(searchValue);
//        } else if ("nickname".equals(searchType)) {
//            searchResults = inquiryService.findByUserNicknameContaining(searchValue);
//        } else if ("hashtag".equals(searchType)) {
//            searchResults = inquiryService.findByHashtagsContaining(searchValue);
        } else {
            // Handle invalid search type
            searchResults = Collections.emptyList();
        }
        System.out.println("이건 성공 해야해");
        model.addAttribute("searchResults", searchResults);

        return "news/inquiry/searchResults";
    }




}








