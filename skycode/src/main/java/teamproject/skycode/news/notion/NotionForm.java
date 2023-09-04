package teamproject.skycode.news.notion;

import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
public class NotionForm {
    private Long id;
    private String notionTitle;
    private String notionContent;
    private LocalDateTime regTime; // 1대1문의 올린 시간

    public Notion toEntity(){return new Notion(id, notionTitle, notionContent, regTime);}
}
