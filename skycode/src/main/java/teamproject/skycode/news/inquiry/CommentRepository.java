package teamproject.skycode.news.inquiry;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 기타 댓글 관련 메서드 추가 가능
}
