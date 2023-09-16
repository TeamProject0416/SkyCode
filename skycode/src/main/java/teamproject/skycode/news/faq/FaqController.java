package teamproject.skycode.news.faq;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import teamproject.skycode.login.MemberEntity;
import teamproject.skycode.login.MemberRepository;
import teamproject.skycode.login.MemberUtils;
import teamproject.skycode.news.notion.Notion;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/news")
public class FaqController {

    @Autowired
    private FaqRepository faqRepository;

    @Autowired
    private FaqService faqService;

    private MemberEntity memberEntity;

    private MemberRepository memberRepository;


    @GetMapping(value = "/faqUp")
    public String newsFaqUp(Model model){
        model.addAttribute("faqForm", new FaqForm());
        System.out.println("1234");
        return "news/faq/faqUp";
    }

    @PostMapping(value = "/faq/faq")
    public String createFaq(@ModelAttribute FaqForm faqForm, Model model){
        if (faqForm.getFaqQuestion().isEmpty() || faqForm.getFaqAnswer().isEmpty()) {
            // 필수 필드가 비어있는 경우
            String errorMsg = "질문과 답변을 모두 입력해주세요.";
            model.addAttribute("errorMsg", errorMsg);
            return "news/faq/faqUp"; // 에러 메시지와 함께 원래의 입력 페이지로 돌아갑니다.
        }
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

        // Check if the user is an admin
        boolean isAdmin = checkIfUserIsAdmin();

        model.addAttribute("isAdmin", isAdmin);

        // Create a list of page numbers for the pager
        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pageNumbers.add(i);
        }

        model.addAttribute("faqs", faqPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageNumbers", pageNumbers);

        return "news/faq/faq";
    }

    private boolean checkIfUserIsAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        return authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

//    @GetMapping(value = "/faq/faq")
//    public String newsFaq(Model model){
//        List<Faq> faqs = faqService.getAllFaqs();
//        model.addAttribute("faqs", faqs);
//        return "news/faq/faq";
//    }



}
