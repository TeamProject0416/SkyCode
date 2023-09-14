package teamproject.skycode.news.notion;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
public class NotionForm {
    private Long id;
    private String type;
    private String notionTitle;
    private String notionContent;
    private String filePath;
    private String fileName;

//    private LocalDateTime regTime; // 1대1문의 올린 시간
//    private String fileName;
//    private MultipartFile filce;

    public Notion toEntity(){
        Notion notion = new Notion();
        notion.setId(this.id);
        notion.setType(this.type);
        notion.setNotionTitle(this.notionTitle);
        notion.setNotionContent(this.notionContent);
        return notion;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

        private String base64Image;



    private String imagePath;

    // 기존의 메서드들...

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
