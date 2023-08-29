package teamproject.skycode.review;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@AllArgsConstructor
@NoArgsConstructor
@Data   // Getter Setter ToString
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 리뷰 id
    @Column
    private String reviewTitle; // 리뷰 제목
    @Column
    private String nickName;    // 작성자 닉네임
    @Column
    private String body;    // 리뷰 내용
    @Column
    private LocalDateTime regTime;   // 작성시간


}
