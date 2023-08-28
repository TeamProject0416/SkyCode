package teamproject.skycode.news.inquiry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@Setter
@ToString
public class InquiryFormDto {

    private Long id;

    private String inquiryTitle;

    private String inquiryContent;


}
