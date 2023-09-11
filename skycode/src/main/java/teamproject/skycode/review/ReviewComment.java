package teamproject.skycode.review;

import java.time.LocalDateTime;

public class ReviewComment {
    private Long id;
    private ReviewEntity reviewEntity;  // 게시글 (ID)
    private String content; // 본문
    private LocalDateTime regTime; // 댓글 생성 시간
}
