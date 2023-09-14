package teamproject.skycode.news.inquiry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString

public class InquiryForm {
    private Long id;
    private String type;
    private boolean isPrivate;
//    private String inquiryTitle
    @NotBlank(message = "1 대 1 문의의 내용은 필수 입력값 입니다")
    private String inquiryContent;

    @NotBlank(message = "1 대 1 문의의 제목은 필수 입력값 입니다")
    private String inquiryTitle;


    public Inquiry toEntity() {
        Inquiry inquiry = new Inquiry();
        inquiry.setId(this.id);
        inquiry.setType(this.type);
        inquiry.setIsPrivate(this.isPrivate);
        inquiry.setInquiryTitle(this.inquiryTitle);
        inquiry.setInquiryContent(this.inquiryContent);

        // regTime은 @PrePersist 어노테이션으로 자동 설정될 것이므로 따로 설정하지 않음
        return inquiry;
    }


}


