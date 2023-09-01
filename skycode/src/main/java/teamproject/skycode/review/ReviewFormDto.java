package teamproject.skycode.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


//@ToString
@Getter
@Setter
public class ReviewFormDto {
    private Long id;
    private String reviewTitle;
    private String nickName;
    private String body;
//    private LocalDateTime regTime;

    public Review toEntity() {
        return new Review(id, reviewTitle, nickName, body);
    }


    private List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();
    private List<Long> reviewImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Review createItem(){
        return modelMapper.map(this, Review.class);
    }
//    ReviewFormDto 객체를 기반으로 새로운 Review 객체를 생성
//    this 는 ReviewFormDto 객체 자체를 나타냅니다.
//    ReviewFormDto 객체의 필드값을 가지고 review 객체를 생성

    public static ReviewFormDto of(Review review){
        return modelMapper.map(review, ReviewFormDto.class);
    }
//    Review 객체에서 ReviewFormDto 객체로 매핑
}
