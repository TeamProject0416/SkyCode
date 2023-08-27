package teamproject.skycode.news.faq;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Faq {

    private Long faqId;

    private String faqTitle;

    private String faqContent;

    private List<FaqFile> faqFile;
}
