package teamproject.skycode.news.faq;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "faq")
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String type;

    @Column
    private String faqQuestion;

    @Column
    private String faqAnswer;

    @Column
    private LocalDateTime regTime;

    @PrePersist
    protected void onCreate(){ regTime = LocalDateTime.now(); }

    public void setFaqAnswer(String faqAnswer) {
        // 문자열이 255자 이상이면 다음 열로 이동
        if (faqAnswer.length() > 255) {
            int maxLength = 255; // 각 열의 최대 길이
            int currentIndex = 0;

            while (currentIndex < faqAnswer.length()) {
                int endIndex = currentIndex + maxLength;
                if (endIndex > faqAnswer.length()) {
                    endIndex = faqAnswer.length();
                }

                String chunk = faqAnswer.substring(currentIndex, endIndex);
                if (currentIndex == 0) {
                    this.faqAnswer = chunk;
                } else {
                    // 다음 열로 이동 (예: faqAnswer2는 다음 열의 이름)
                    // 새로운 열에 저장하려면 여기에 저장 로직을 추가하세요.
                }

                currentIndex = endIndex;
            }
        } else {
            this.faqAnswer = faqAnswer;
        }
    }


}
