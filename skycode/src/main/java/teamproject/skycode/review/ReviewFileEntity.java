package teamproject.skycode.review;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "review_file_table")
public class ReviewFileEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private ReviewEntity reviewEntity;

    public static ReviewFileEntity toReviewFileEntity(ReviewEntity reviewEntity, String originalFileName, String storedFileName) {
        ReviewFileEntity reviewFileEntity = new ReviewFileEntity();
        reviewFileEntity.setOriginalFileName(originalFileName);
        reviewFileEntity.setStoredFileName(storedFileName);
        reviewFileEntity.setReviewEntity(reviewEntity);
        System.out.println("ReviewFileEntity storedFileName = " + storedFileName);
//        ReviewFileEntity storedFileName = /review_img/1693918286761_청바지23.jpg
        return reviewFileEntity;
    }

}
