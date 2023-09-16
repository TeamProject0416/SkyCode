package teamproject.skycode.review;


import lombok.*;
import org.springframework.stereotype.Component;
import teamproject.skycode.common.BaseEntity;
import teamproject.skycode.login.MemberEntity;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "review")
@Getter
@Setter
@ToString
@Component
public class ReviewEntity extends BaseEntity {
    @Id
    @Column(name="review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 리뷰 id

    @Column
    private String email; // 작성자 이메일
    @Column
    private String nickName;    // 작성자 닉네임
    @Column
    private String reviewTitle; // 리뷰 제목
    @Column(length = 500)
    private String contents;    // 리뷰 내용

    @Column(updatable = false)
    private int reviewHits;


    private String miniImgName; // 미니 이미지 파일명
    private String miniOriImgName; // 미니 원본 이미지 파일명
    private String miniImgUrl; // 미니 이미지 조회 경로
    private String bigImgName; // 큰 이미지 파일명
    private String bigOriImgName; // 큰 원본 이미지 파일명
    private String bigImgUrl; // 큰 이미지 조회 경로
    private String reviewTime; // "yyyy-MM-dd"

    @OneToMany(mappedBy = "reviewEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    @Transient
    private int commentCount;  // 댓글 수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;
    public void updateReviewImg(String miniImgName, String miniOriImgName, String miniImgUrl,
                                String bigImgName, String bigOriImgName, String bigImgUrl) {
        this.miniImgName = miniImgName;
        this.miniOriImgName = miniOriImgName;
        this.miniImgUrl = miniImgUrl;

        this.bigImgName = bigImgName;
        this.bigOriImgName = bigOriImgName;
        this.bigImgUrl = bigImgUrl;
    }
    public void updateReview(ReviewDto reviewDto) {
        this.nickName = reviewDto.getNickName();
        this.email = reviewDto.getEmail();
        this.reviewTitle = reviewDto.getReviewTitle();
        this.contents = reviewDto.getContents();
        this.reviewHits = reviewDto.getReviewHits();

        this.miniImgName = reviewDto.getMiniImgName();
        this.miniOriImgName = reviewDto.getMiniOriImgName();
        this.miniImgUrl = reviewDto.getMiniImgUrl();

        this.bigImgName = reviewDto.getBigImgName();
        this.bigOriImgName = reviewDto.getBigOriImgName();
        this.bigImgUrl = reviewDto.getBigImgUrl();
    }

}
