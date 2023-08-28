package teamproject.skycode.news.inquiry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class InquiryFormDto {

    private Long id;

    private String nickName;

    private String inquiryTitle;

    private String inquiryContent;

    private LocalDateTime regTime; // 1대1문의 등록 시간



}
