package teamproject.skycode.news.notion;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
//@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "notion")
public class Notion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 공지글 id

    @Column(nullable = false)
    private String type;    // 공지글 종류

    @Column(nullable = false)
    private String notionTitle;     // 공지글 제목

    @Column(nullable = false)
    private String notionContent;   // 공지글 내용

    @Column
    private LocalDateTime regTime; // 공지사항 올린 시간

    // 생성 시간을 현재 시간으로 설정
    @PrePersist
    protected void onCreate() {
        regTime = LocalDateTime.now();
    }

    private String fileName;

    private byte[] imageData; // Byte array to store image data

    // Constructors, getters, and setters


    // Getter and setter for imageData
    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

}
