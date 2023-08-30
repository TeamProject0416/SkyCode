package teamproject.skycode.news.faq;

import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
public class FaqForm {

    private Long id;

    private String faqType;

    private String faqQuestion;

    private String faqAnswer;

    private LocalDateTime regTime;

    public Faq toEntity() {return new Faq(id, faqType, faqQuestion, faqAnswer, regTime); }
}
