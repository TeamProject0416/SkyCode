package teamproject.skycode.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;

    public Long save(CommentDTO commentDTO) {
        /* 부모엔티티(BoardEntity) 조회 */
        Optional<ReviewEntity> optionalBoardEntity = reviewRepository.findById(commentDTO.getReviewId() );
        if (optionalBoardEntity.isPresent()) {
            ReviewEntity reviewEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, reviewEntity);
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }

    public List<CommentDTO> findAll(Long reviewId) {
        ReviewEntity reviewEntity = reviewRepository.findById(reviewId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByReviewEntityOrderByIdDesc(reviewEntity);
        /* EntityList -> DTOList */
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity: commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, reviewId);
            commentDTOList.add(commentDTO);
        }
        System.out.println("Time111 = " + reviewEntity.getCreatedTime());
        return commentDTOList;
    }

}
