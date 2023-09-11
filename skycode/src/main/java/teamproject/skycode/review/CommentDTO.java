package teamproject.skycode.review;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long reviewId;
    private LocalDateTime commentCreatedTime;

    public static CommentDTO toCommentDTO(CommentEntity commentEntity, Long reviewId) {

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setCommentCreatedTime(commentEntity.getCreatedTime());
        commentDTO.setReviewId(reviewId);
        return commentDTO;
    }
//public static CommentDTO createCommentDto(CommentEntity comment) {
//
//    return new CommentDTO(
//            comment.getId(),
//            comment.getReviewEntity().getId(),
//            comment.getCommentWriter(),
//            comment.getCommentContents()
//    );
//}

}
