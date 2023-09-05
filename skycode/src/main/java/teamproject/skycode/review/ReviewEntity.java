package teamproject.skycode.review;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "review_table")
@Getter
@Setter
public class ReviewEntity extends BaseEntity {
    @Id
    @Column(name="review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 리뷰 id
    @Column
    private String nickName;    // 작성자 닉네임
    @Column
    private String reviewTitle; // 리뷰 제목
    @Column(length = 500)
    private String contents;    // 리뷰 내용

    @Column(updatable = false)
    private int reviewHits;

    @Column
    private int fileAttached; // 1 or 0

    @OneToMany(mappedBy = "reviewEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ReviewFileEntity> reviewFileEntityList = new ArrayList<>();


    @OneToMany(mappedBy = "reviewEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();



    public static ReviewEntity toSaveEntity(ReviewDto reviewDto) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setNickName(reviewDto.getNickName());
        reviewEntity.setReviewTitle(reviewDto.getReviewTitle());
        reviewEntity.setContents(reviewDto.getContents());
        reviewEntity.setReviewHits(0);
        return reviewEntity;
    }

    public static ReviewEntity toUpdateEntity(ReviewDto reviewDto) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setId(reviewDto.getId());
        reviewEntity.setNickName(reviewDto.getNickName());
        reviewEntity.setReviewTitle(reviewDto.getReviewTitle());
        reviewEntity.setContents(reviewDto.getContents());
        return reviewEntity;
    }

    public static ReviewEntity toSaveFileEntity(ReviewDto reviewDto) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setNickName(reviewDto.getNickName());
        reviewEntity.setReviewTitle(reviewDto.getReviewTitle());
        reviewEntity.setContents(reviewDto.getContents());
        reviewEntity.setReviewHits(0);
        reviewEntity.setFileAttached(1); // 파일 있음.
        return reviewEntity;
    }

}
