package teamproject.skycode.news.inquiry;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    // 게시글 ID (댓글이 어떤 게시글에 속하는지 식별)
    private Long postId;



    // 생성자, getter, setter, 기타 메서드
}
