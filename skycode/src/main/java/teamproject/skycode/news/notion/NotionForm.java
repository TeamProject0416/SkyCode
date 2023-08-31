package teamproject.skycode.news.notion;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class NotionForm {
    private Long id;
    private String type;
    private String notionTitle;
    private String notionContent;
    private LocalDateTime regTime; // 1대1문의 올린 시간

    public Notion toEntity(){
        Notion notion = new Notion();
        notion.setType(this.type);
        notion.setNotionTitle(this.notionTitle);
        notion.setNotionContent(this.notionContent);
        notion.setRegTime(this.regTime);
        return notion;
    }
}
