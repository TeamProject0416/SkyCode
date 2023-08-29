package teamproject.skycode.news.inquiry;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
public class InquiryForm {

    private Long id;
    private String nickName;
    private String inquiryTitle;
    private String inquiryContent;
    private LocalDateTime regTime; // 1대1문의 등록 시간

    public Inquiry toEntity() {return new Inquiry(id, nickName, inquiryTitle, inquiryContent, regTime);}
}
