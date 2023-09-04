package teamproject.skycode.news.inquiry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
//@Table(name = "inquiry")

public class  Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 문의글 id

    @Column(nullable = false)
    private String type;    // 문의글 종류

    private boolean isPrivate;  // 공개여부

    @Column(nullable = false)
    private String inquiryTitle;    // 문의글 제목

    @Column(nullable = false)
    private String inquiryContent;  // 문의글 내용

    @Column(nullable = false)
    private LocalDateTime regTime;  // 등록 시간

    private int viewCount; // Add viewCount field


    // 생성자, getter, setter, toString 등의 메서드 생략

    // 생성 시간을 현재 시간으로 설정
    @PrePersist
    protected void onCreate() {
        regTime = LocalDateTime.now();
    }

    // 공개여부
    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    @OneToMany(mappedBy = "reviewEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> commentEntityList = new ArrayList<>();


}
