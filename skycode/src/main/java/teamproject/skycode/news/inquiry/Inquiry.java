package teamproject.skycode.news.inquiry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@ToString
@Entity
//@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "inquiry")

public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    private boolean isPrivate;

    @Column(nullable = false)
    private String inquiryTitle;

    @Column(nullable = false)
    private String inquiryContent;

    @Column(nullable = false)
    private LocalDateTime regTime;

    // 생성자, getter, setter, toString 등의 메서드 생략

    // 생성 시간을 현재 시간으로 설정
    @PrePersist
    protected void onCreate() {
        regTime = LocalDateTime.now();
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

//    public void setIsPrivate(boolean isPrivate) {
//        this.isPrivate = isPrivate;
//    }


//    @Id
//    @Column(name = "inquiry_id")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id; // 1대1문의 아이디
//
//    @Column(nullable = false)
//    private String inquiryTitle; // 1대1문의 제목
//
//    @Column(nullable = false)
//    private String inquiryContent; // 1대1문의 내용
//
//    @Enumerated(EnumType.STRING)
//    private Type type;
////
////    @Column
//    private Boolean isPrivate;
//
//    @Column
//    @CreationTimestamp
//    private LocalDateTime regTime; // 1대1문의 등록 시간
//
//    public Inquiry(Long id, String inquiryTitle, String inquiryContent, Type type, Boolean isPrivate, LocalDateTime regTime) {
//        this.id = id;
//        this.inquiryTitle = inquiryTitle;
//        this.inquiryContent = inquiryContent;
//        this.type = type;
//        this.isPrivate = isPrivate;
//        this.regTime = regTime;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Boolean getIsPrivate() {
//        return isPrivate;
//    }
//
//    public void setIsPrivate(Boolean isPrivate) {
//        this.isPrivate = isPrivate;
//    }
//
//    public String getTitle() {
//        return inquiryTitle;
//    }
//
//    public void setTitle(String title) {
//        this.inquiryTitle = title;
//    }
//
//    public String getContent() {
//        return inquiryContent;
//    }
//
//    public void setContent(String content) {
//        this.inquiryContent = content;
//    }
}
