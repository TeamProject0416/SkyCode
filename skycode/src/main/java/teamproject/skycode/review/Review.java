package teamproject.skycode.review;


import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@AllArgsConstructor
//@NoArgsConstructor
//@Data   // Getter Setter ToString
@Getter
@Setter
@ToString
public class Review extends BaseEntity {
    @Id
    @Column(name="review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 리뷰 id
    @Column
    private String reviewTitle; // 리뷰 제목
    @Column
    private String nickName;    // 작성자 닉네임
    @Column
    private String body;    // 리뷰 내용

//    private LocalDateTime regTime;   // 작성시간

}
