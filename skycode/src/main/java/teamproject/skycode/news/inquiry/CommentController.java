package teamproject.skycode.news.inquiry;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/news")
public class CommentController {
    @GetMapping("/inquiryShow")
    public String showInquiryPage(Model model) {
        // 이 메서드에서 모델에 필요한 데이터를 추가
        // 예: inquiry 정보를 모델에 추가하고 뷰를 반환
        System.out.println("댓글");
        return "news/inquiry/inquiryShow";
    }

//    @PostMapping("/submitComment")
//    public String submitComment(CommentForm commentForm) {
//
//        InquiryForm inquiryForm = new InquiryForm();
//        Long id = inquiryForm.getId(); // 게시물 ID 가져오기
//// commentForm에서 댓글 데이터를 가져와서 처리합니다.
//        String commentText = commentForm.getComment();
//        System.out.println(id);
//
//        // 여기에서 댓글을 저장하고 다른 로직을 수행합니다.
//        // 예를 들어, 데이터베이스에 저장하거나 서비스를 호출할 수 있습니다.
//        System.out.println("댓글 보내기");
//        // 처리 후, 리다이렉트 또는 다른 화면으로 이동합니다.
//        return "redirect:/news/inquiry/inquiryShow/" + id;
//    }
}
