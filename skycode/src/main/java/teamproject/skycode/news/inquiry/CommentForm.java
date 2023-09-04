package teamproject.skycode.news.inquiry;

import lombok.Data;

@Data
public class CommentForm {

    private Long postId;

    private String content;


    public String getComment() {
        return content;
    }

    public Long getPostId() {
        return postId;
    }
}
