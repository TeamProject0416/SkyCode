package teamproject.skycode.review;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;


// DTO(Data Transfer Object), VO, Bean,         Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private String nickName;
    private String reviewTitle;
    private String contents;
    private int reviewHits;
    private LocalDateTime reviewCreatedTime;
    private LocalDateTime reviewUpdatedTime;

    private MultipartFile reviewFile; // save.html -> Controller 파일 담는 용도
    private String originalFileName; // 원본 파일 이름
    private String storedFileName; // 서버 저장용 파일 이름
    private int fileAttached; // 파일 첨부 여부(첨부 1, 미첨부 0)

    public ReviewDto(Long id, String nickName, String reviewTitle,  int reviewHits, LocalDateTime reviewCreatedTime) {
        this.id = id;
        this.nickName = nickName;
        this.reviewTitle = reviewTitle;
        this.reviewHits = reviewHits;
        this.reviewCreatedTime = reviewCreatedTime;
    }

    public static ReviewDto toReviewDto(ReviewEntity reviewEntity) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(reviewEntity.getId());
        reviewDto.setNickName(reviewEntity.getNickName());
        reviewDto.setReviewTitle(reviewEntity.getReviewTitle());
        reviewDto.setContents(reviewEntity.getContents());
        reviewDto.setReviewHits(reviewEntity.getReviewHits());
        reviewDto.setReviewCreatedTime(reviewEntity.getCreatedTime());
        reviewDto.setReviewUpdatedTime(reviewEntity.getUpdatedTime());
        if (reviewEntity.getFileAttached() == 0) {
            reviewDto.setFileAttached(reviewEntity.getFileAttached()); // 0
        } else {
            reviewDto.setFileAttached(reviewEntity.getFileAttached()); // 1
            // 파일 이름을 가져가야 함.
            // orginalFileName, storedFileName : review_file_table(ReviewFileEntity)
            // join
            // select * from review_table r, review_file_table rf where r.id=rf.review_id
            // and where r.id=?
            reviewDto.setOriginalFileName(reviewEntity.getReviewFileEntityList().get(0).getOriginalFileName());
            reviewDto.setStoredFileName(reviewEntity.getReviewFileEntityList().get(0).getStoredFileName());
        }

        return reviewDto;
    }

//    public ReviewEntity toEntity() {
//        return new ReviewEntity(id, reviewTitle, nickName, body);
//    }


//    private List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();
//    private List<Long> reviewImgIds = new ArrayList<>();
//
//    private static ModelMapper modelMapper = new ModelMapper();
//
//    public ReviewEntity createItem(){
//        return modelMapper.map(this, ReviewEntity.class);
//    }
////    ReviewFormDto 객체를 기반으로 새로운 Review 객체를 생성
////    this 는 ReviewFormDto 객체 자체를 나타냅니다.
////    ReviewFormDto 객체의 필드값을 가지고 review 객체를 생성
//
//    public static ReviewDto of(ReviewEntity reviewEntity){
//        return modelMapper.map(reviewEntity, ReviewDto.class);
//    }
//    Review 객체에서 ReviewFormDto 객체로 매핑
}
