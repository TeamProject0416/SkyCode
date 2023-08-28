package teamproject.skycode.news.inquiry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.catalina.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "inquiry")

public class Inquiry {

    @Id
    @Column(name = "inquiry_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 1대1문의 아이디

    @Column(nullable = false)
    private String inquiryTitle; // 1대1문의 제목

    @Column(nullable = false)
    private String inquiryContent; // 1대1문의 내용

    @Column(nullable = false)
    private boolean inquiryLooking; // 1대1문의 나만보기

//    private List<InquiryFile> inquiryFiles; // 1대1문의 파일

    private LocalDateTime regTime; // 1대1문의 올린 시간

}
