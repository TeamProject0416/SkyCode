package teamproject.skycode.review;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    private Long id;    // 리뷰 id
    private String reviewTitle; // 리뷰 제목
    private String nickName;    // 작성자 닉네임
    private String body;    // 리뷰 내용
    private String hashTag; // 해시태그
    private LocalDateTime regTime;   // 작성시간

}
