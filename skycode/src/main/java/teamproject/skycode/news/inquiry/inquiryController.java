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
//@RequiredArgsConstructor
public class inquiryController {

//    @Autowired
//    private InquiryRepository inquiryRepository;
//    @Autowired
//    private final InquiryService inquiryService;
//
//    @Autowired
//    public inquiryController(InquiryService inquiryService) {
//        this.inquiryService = inquiryService;
//    }
//
//
//    @GetMapping(value = "/inquiry")
//    public String newsInquiry(){
//        return "news/inquiry/inquiry";
//    }
//
//    @PostMapping("/inquiry/create")
//    public String createInquiry(InquiryForm form){
//        System.out.println(form.toString());
//
//        Inquiry inquiry = form.toEntity();
//        System.out.println(inquiry.toString());
//
//        Inquiry saved = inquiryRepository.save(inquiry);
//        System.out.println(saved.toString());
//        Inquiry savedInquiry = inquiryRepository.save(inquiry);
//
//
//        return "news/inquiry/inquiryShow" + savedInquiry.getId() ;
//    }


    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private InquiryService inquiryService;

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
        System.out.println("4");

// InquiryService에 해당 메소드 구현 필요
        model.addAttribute("inquiries", inquiries);
        System.out.println("432");

        // 조회된 데이터가 없을 경우 예외 처리 등을 수행
        return "/news/inquiry/inquiryList"; // 혹은 다른 페이지로 이동
    }


//
//    @GetMapping("/inquiryShow/{id}") // id는 조회할 문의의 고유 ID
//    public String showInquiryDetails(@PathVariable Long id, Model model) {
//        Inquiry inquiry = inquiryRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid inquiry Id:" + id));
//
//        model.addAttribute("inquiry", inquiry);
//        return "/news/inquiry/inquiryShow";
//    }
//    @PostMapping("/inquiryShow/{id}")
//    public String showInquiry(InquiryForm inquiryForm, Model model) {
//        // InquiryForm을 이용하여 Inquiry 객체를 생성하거나 데이터베이스에서 조회
//        Inquiry inquiry = inquiryForm.toEntity();
//

// 조회한 Inquiry 객체를 모델에 추가
//        model.addAttribute("inquiry", inquiry);
//
//        return "news/inquiry/inquiryShow";
//    }



    @GetMapping("/inquiryList")
    public String getInquiryList(Model model) {
        // 조회 등 필요한 작업 수행
        // ...

        return "news/inquiry/inquiryList";
    }



//    @PostMapping("/news/inquiryList")
//    public String postInquiry(@ModelAttribute InquiryForm inquiryForm) {
//
//        Inquiry inquiryFormEntity = inquiryForm.toEntity(); // toEntity() 메서드 호출
//        inquiryRepository.save(inquiryFormEntity); // 데이터베이스에 저장
//
//        Inquiry inquiry = new Inquiry();
//        inquiry.setType(inquiryForm.getType());
//        inquiry.setIsPrivate(inquiryForm.isPrivate());
//        inquiry.setInquiryTitle(inquiryForm.getInquiryTitle());
//        inquiry.setInquiryContent(inquiryForm.getInquiryContent());
//
//
//        inquiryRepository.save(inquiry); // 데이터베이스에 저장
//
//        return "redirect:/news/inquiry/inquiryList";
//    }



//    @GetMapping(value = "/inquiryShow")
//    public String newsInquiryShow(){
//        return "news/inquiry/inquiryShow";
//    }



}




