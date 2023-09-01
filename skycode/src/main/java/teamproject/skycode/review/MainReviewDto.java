package teamproject.skycode.review;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainReviewDto {
    private Long id;
    private String reviewTitle;
    private String nickName;
    private String body;
    private String imgUrl;


    @QueryProjection
    public MainReviewDto(Long id, String reviewTitle, String nickName, String body, String imgUrl) {
        this.id = id;
        this.reviewTitle = reviewTitle;
        this.nickName = nickName;
        this.body = body;
        this.imgUrl = imgUrl;
    }
}
