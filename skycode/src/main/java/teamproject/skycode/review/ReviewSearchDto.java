package teamproject.skycode.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewSearchDto {
    private String searchBy;
    private String searchQuery = "";
}
