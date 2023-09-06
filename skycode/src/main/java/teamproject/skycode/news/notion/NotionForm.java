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

//    private LocalDateTime regTime; // 1대1문의 올린 시간
//    private String fileName;
//    private MultipartFile filce;

    public Notion toEntity(){
        Notion notion = new Notion();
        notion.setId(this.id);
        notion.setType(this.type);
        notion.setNotionTitle(this.notionTitle);
        notion.setNotionContent(this.notionContent);
//        notion.setRegTime(this.regTime);


        return notion;
    }

//    public void setFileName(String uploadedFileName) {
//        this.fileName = uploadedFileName;
//    }
//
//    public MultipartFile getFile() {
//        return filce;
//    }

}
