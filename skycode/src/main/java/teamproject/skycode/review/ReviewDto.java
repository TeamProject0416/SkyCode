package teamproject.skycode.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Long id;
    private Long userId;
    private String userNickname;
    private String reviewBody;

    public static ReviewDto createReviewDto(Review review) {
        return new ReviewDto(
                review.getId(),
                review.getRegTime(),
                review.getNickname(),
                review.getBody()
        );
    }
}
