package teamproject.skycode.news.inquiry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDTO {

    private Long id;
    private Long postId;
    private String content;
    private LocalDateTime createdAt;


    // 생성자, 게터, 세터, toString 등 필요한 메서드를 추가합니다.

    public CommentDTO(Long id, Long postId, String content, LocalDateTime createdAt) {
        this.id = id;
        this.postId = postId;
        this.content = content;
        this.createdAt = createdAt;

    }

    public Long getInquiryId() {
        
    }

// 게터, 세터, toString 등 필요한 메서드를 추가합니다.

// 나머지 메서드와 게터, 세터 등은 생략하였습니다. 실제 필요한 대로 추가해주세요.
}