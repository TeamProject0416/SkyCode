package teamproject.skycode.news.inquiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 게시글에 대한 댓글 목록 조회
    @GetMapping("/news/submitComment")
    public String getComments(@PathVariable Long postId, Model model) {
        List<Comment> comments = commentService.findByPostId(postId);
        model.addAttribute("comments", comments);
        System.out.println("성공일까");
        return "comment_list"; // 댓글 목록을 표시할 Thymeleaf 템플릿 이름
    }

    // 댓글 작성
    @PostMapping
    public String createComment(@ModelAttribute Comment comment) {
        commentService.createComment(comment);
        return "redirect:/comments/" + comment.getPostId(); // 댓글 목록 페이지로 리다이렉트
    }
}
