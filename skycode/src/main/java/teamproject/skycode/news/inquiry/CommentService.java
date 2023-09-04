package teamproject.skycode.news.inquiry;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // 게시글 ID에 해당하는 댓글 목록 조회
    public List<Comment> findByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    // 댓글 작성
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
