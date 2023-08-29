package teamproject.skycode.review;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
public class ReviewFormDto {
    private Long id;
    private String reviewTitle;
    private String nickName;
    private String body;
    private LocalDateTime regTime;

    public Review toEntity() {
        return new Review(id, reviewTitle, nickName, body, regTime);
    }
}
