package teamproject.skycode.news.faq;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class FaqForm {

    private Long id;
    private String type;
    private String faqQuestion;
    private String faqAnswer;
    private LocalDateTime regTime;

    public Faq toEntity() {
        Faq faq = new Faq();
        faq.setId(this.id);
        faq.setType(this.type);
        faq.setFaqQuestion(this.faqQuestion);
        faq.setFaqAnswer(this.faqAnswer);
        faq.setRegTime(this.regTime);
        return faq;
    }
}
