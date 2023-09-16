package teamproject.skycode.news.inquiry;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import teamproject.skycode.login.MemberEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "inquiry")

public class  Inquiry {

    @Id
    @Column(name="inquiry_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 문의글 id

    @Column
    private String email; // 작성자의 이메일

    @Column
    private String nickName;    // 작성자 닉네임

    @Column(nullable = false)
    private String type;    // 문의글 종류

    private boolean isPrivate;  // 공개여부

    @Column(nullable = false, length = 1000)
    @NotBlank(message = "1 대 1 문의의 제목은 필수 입력값 입니다")
    private String inquiryTitle;    // 문의글 제목

    @Column(nullable = false, length = 4000)
    @NotBlank(message = "1 대 1 문의의 내용은 필수 입력값 입니다")
    private String inquiryContent;  // 문의글 내용

    @CreationTimestamp
    @Column(name = "reg_time")
    private LocalDateTime regTime;  // 등록 시간

//    private String writer;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity writer;

//    public String getWriter() {
//        return writer;
//    }


    public LocalDateTime getRegistrationTime() {
        return regTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.regTime = registrationTime;
    }


    private int viewCount; // Add viewCount field

    // 생성자, getter, setter, toString 등의 메서드 생략

    // 생성 시간을 현재 시간으로 설정
    @PrePersist
    protected void onCreate() {
        regTime = LocalDateTime.now();
    }

    // 공개여부
//    public boolean isPrivate() {
//        return isPrivate;
//    }

    // 공개여부
    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    private String responseContent; // 답변 내용

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    public boolean isOwner(String userEmail) {
        return this.writer.getEmail().equals(userEmail);
    }
}
