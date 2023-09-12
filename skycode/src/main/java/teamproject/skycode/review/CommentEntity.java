package teamproject.skycode.review;

import lombok.Getter;
import lombok.Setter;
import teamproject.skycode.common.BaseEntity;
import teamproject.skycode.login.MemberEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)
    private String commentWriter;
    @Column(length = 100)
    private String commentContents;

    /* Review : Comment = 1 : N*/
    /* 댓글 기준 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private ReviewEntity reviewEntity;

    public static CommentEntity toSaveEntity(CommentDTO commentDTO, ReviewEntity reviewEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setReviewEntity(reviewEntity);
        return commentEntity;
    }

}
