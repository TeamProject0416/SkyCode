package teamproject.skycode.news.notion;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Notion {

    private Long notionId;

    private String notionTitle;

    private String notionContent;

    private List<NotionFile> notionFiles;
}
