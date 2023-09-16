package teamproject.skycode.news.notion;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "notion")
public class Notion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 공지글 id

    @Column(nullable = false)
    private String type;    // 공지글 종류


    @Column(nullable = false, length = 1000) // 예시로 1000 글자까지 저장 가능하도록 설정
    @NotBlank(message = "Notion title is required")
    private String notionTitle;

    @Column(length = 4000) // 예시로 4000 글자까지 저장 가능하도록 설정
    @Size(min = 0)
    private String notionContent;

    @CreationTimestamp
    @Column(name = "reg_time")
    private LocalDateTime regTime; // 공지사항 올린 시간

    @Column(name = "file_path")
    private String filePath; // 업로드된 파일의 경로

    private String fileName; // 업로드된 파일의 이름

    @Column(length = 10000) // 충분한 길이로 조정
    private String base64Image;


    // Getter와 Setter를 추가하세요
    public String getBase64Image() {
        return base64Image;
    }

    private String saveImage(MultipartFile file) throws IOException {
        String uploadDir = "/SkyCodeProject/img/notion/"; // 이미지를 저장할 경로
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        Files.write(filePath, file.getBytes());
        return fileName;
    }
    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public LocalDateTime getRegistrationTime() {
        return regTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.regTime = registrationTime;
    }

    @Column(name = "view_count", nullable = false, columnDefinition = "int default 0")
    private int countView;



    // 생성 시간을 현재 시간으로 설정
    @PrePersist
    protected void onCreate() {
        regTime = LocalDateTime.now();
    }



}
