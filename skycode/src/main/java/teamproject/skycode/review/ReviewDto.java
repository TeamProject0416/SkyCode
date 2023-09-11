package teamproject.skycode.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;
import teamproject.skycode.event.EventEntity;
import teamproject.skycode.event.EventFormDto;


import javax.persistence.Column;
import javax.persistence.ElementCollection;
import java.time.LocalDateTime;
import java.util.List;


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
//    private LocalDateTime reviewCreatedTime;
//    private LocalDateTime reviewUpdatedTime;

    private String reviewTime; // "yyyy-MM-dd"

    private String miniImgName; // 미니 이미지 파일명
    private String miniOriImgName; // 미니 원본 이미지 파일명
    private String miniImgUrl; // 미니 이미지 조회 경로

//    @ElementCollection
//    @Column(name = "big_img_name")
//    private List<String> bigImgNameList; // 큰 이미지 파일명
//    private List<String> bigOriImgNameList; // 큰 원본 이미지 파일명
//    private List<String> bigImgUrlList; // 큰 이미지 조회 경로

    private String bigImgName; // 큰 이미지 파일명
    private String bigOriImgName; // 큰 원본 이미지 파일명
    private String bigImgUrl; // 큰 이미지 조회 경로

    private static ModelMapper modelMapper = new ModelMapper();

    private Long memberId;

    public ReviewEntity createReview() {
        return modelMapper.map(this, ReviewEntity.class);
    }
    // ReviewDto 객체를 기반으로 새로운 Review 객체를 생성
    // this 는 ReviewDto 객체 자체를 나타냅니다
    // ReviewDto 객체의 필드값을 가지고 Review 객체를 생성
    public static ReviewDto of(ReviewEntity review) {
        return modelMapper.map(review, ReviewDto.class);
    }
    // Review 객체에서 ReviewDto 객체로 매핑






//    public List<String> getBigImgNameList() {
//        return bigImgNameList;
//    }
//
//    public List<String> getBigOriImgNameList() {
//        return bigOriImgNameList;
//    }




}
