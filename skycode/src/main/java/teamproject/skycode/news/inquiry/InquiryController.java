package teamproject.skycode.news.inquiry;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.Collections;

import java.util.List;

@Controller
@RequestMapping("/news")
public class InquiryController {


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

    // 1 대 1 문의폼 화면 출력
    @GetMapping("/inquiry")
    public String showInquiryForm(Model model) {
        model.addAttribute("inquiryForm", new InquiryForm());
        return "/news/inquiry/inquiry";
    }

    // 1 대 1 문의 등록시 전송하는 것
    @PostMapping("/inquiry/create")
    public String submitInquiry(@ModelAttribute InquiryForm inquiryForm) {
        // InquiryForm을 Inquiry 엔티티로 변환하여 저장
        Inquiry inquiry = inquiryForm.toEntity(); // InquiryForm에서 Inquiry 엔티티로 변환하는 메서드 필요
        inquiryRepository.save(inquiry);
        return "redirect:/news/inquiry/inquiryList"; // 저장 후 등록 날짜 오름차순으로 정렬된 목록 페이지로 리다이렉트
    }

    // 1 대 1 문의 리스트 화면 출력
    @GetMapping("/inquiry/inquiryList")
    public String getInquiries(Model model, @RequestParam(defaultValue = "0") int page, String sortBy) {
        int pageSize = 10;
        Page<Inquiry> inquiryPage = inquiryRepository.findAll(PageRequest.of(page, pageSize));
        // 리스트들의 등록시간 호출
        List<Inquiry> inquiries = inquiryRepository.findAllOrderByRegistrationTimeDesc();

        // 조회수
        long totalInquiryCount = inquiryService.getTotalInquiryCount();
        model.addAttribute("totalInquiryCount", totalInquiryCount);
        // popularity = 조회수 순으로 정렬
        if ("popularity".equals(sortBy)) {
            inquiries = inquiryService.getAllInquiriesSortedByPopularity();
            System.out.println("될까");
        } else {
            // 날짜 내림차순으로 정렬
            // Default to sorting by date (most recent first)
            inquiries = inquiryService.getAllInquiriesSortedByDate();
        }
        System.out.println("된다");

        model.addAttribute("inquiries", inquiries);
        // 페이지 노출문의글 제한
        model.addAttribute("inquiries", inquiryPage);
//        return "inquiry-list"; // This should match your Thymeleaf template name
        return "news/inquiry/inquiryList"; // 혹은 다른 페이지로 이동

    }


    // 1 대 1 문의 서브페이지 화면으로 보내기
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



    // 1 대 1 문의 서브페이지 화면 출력
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

    // 검색하기
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

    // 문의글 삭제
    @PostMapping("/inquiry/delete")
    public String deleteInquiry(@RequestParam Long inquiryId) {
        inquiryService.deleteInquiry(inquiryId);
        System.out.println("삭제");
        return "redirect:/news/inquiry/inquiryList"; // 삭제 후 이동할 페이지를 지정합니다.
    }

    // 1 대 1 문의글 수정 페이지 출력
    @GetMapping("/inquiry/edit/{inquiryId}")
    public String showEditForm(@PathVariable Long inquiryId, Model model) {
        // inquiryId를 사용하여 해당 문의 내용을 불러오는 로직을 추가
        Inquiry inquiry = inquiryService.findById(inquiryId);

        // 조회한 문의 내용을 폼 객체에 매핑
        InquiryForm inquiryForm = new InquiryForm();
        inquiryForm.setId(inquiry.getId());
        inquiryForm.setType(inquiry.getType());
        inquiryForm.setPrivate(inquiry.isPrivate());
        inquiryForm.setInquiryTitle(inquiry.getInquiryTitle());
        inquiryForm.setInquiryContent(inquiry.getInquiryContent());

        model.addAttribute("inquiryForm", inquiryForm);
        System.out.println("수정중");

        return "news/inquiry/edit";
    }

    // 1 대 1 문의글 수정 보내기
    @PostMapping("/inquiry/edit")
    public String editInquiry(@ModelAttribute("inquiryForm") InquiryForm inquiryForm) {
        // 폼 데이터를 기반으로 수정된 내용을 저장하는 로직을 구현합니다.
        // InquiryForm을 Inquiry 엔티티로 변환하고 엔티티를 저장하는 방식으로 구현할 수 있습니다.
        Inquiry inquiry = new Inquiry();
        inquiry.setId(inquiryForm.getId());
        inquiry.setType(inquiryForm.getType());
        inquiry.setPrivate(inquiryForm.isPrivate());
        inquiry.setInquiryTitle(inquiryForm.getInquiryTitle());
        inquiry.setInquiryContent(inquiryForm.getInquiryContent());
        System.out.println("수정완료");
        System.out.println(inquiryForm.isPrivate());

        inquiry.setRegTime(LocalDateTime.now()); // Current timestamp

        inquiryService.editInquiry(inquiry);

        // 수정된 상세 페이지로 리다이렉트
        return "redirect:/news/inquiry/show/" + inquiry.getId();
    }

}







