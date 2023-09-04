package teamproject.skycode.news.inquiry;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
public class CommentService {

    private final CommentRepository commentRepository;

    private final InquiryRepository inquiryRepository;

    public Long save(CommentDTO commentDTO) {
        /* 부모엔티티(BoardEntity) 조회 */
        Optional<Inquiry> optionalBoardEntity = inquiryRepository.findById(commentDTO.getId() );
        if (optionalBoardEntity.isPresent()) {
            Inquiry inquiry = optionalBoardEntity.get();
            Comment comment = Comment.toSaveEntity(commentDTO, inquiry);
            return commentRepository.save(comment).getId();
        } else {
            return null;
        }
    }

    public List<CommentDTO> findAll(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id).get();
        List<Comment> commentList = commentRepository.findAllByInquiryOrderByIdDesc(inquiry);
        /* EntityList -> DTOList */
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment: commentList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(comment, id);
            commentDTOList.add(commentDTO);
        }
        System.out.println("Time111 = " + inquiry.getCreatedTime());
        return commentDTOList;
    }

//    @Autowired
//    public CommentService(CommentRepository commentRepository) {
//        this.commentRepository = commentRepository;
//    }
//
//    // 게시글 ID에 해당하는 댓글 목록 조회
//    public List<Comment> findByPostId(Long postId) {
//        return commentRepository.findByPostId(postId);
//    }
//
//    // 댓글 작성
//    public Comment createComment(Comment comment) {
//        return commentRepository.save(comment);
//    }
}
