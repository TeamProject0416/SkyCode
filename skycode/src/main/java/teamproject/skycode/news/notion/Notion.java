package teamproject.skycode.news.notion;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notion")
public class Notion {

    @Id
    @Column(name = "notion_id")
    private Long id;

    @Column
    private String notionTitle;

    @Column
    private String notionContent;

    @Column
    private LocalDateTime regTime; // 1대1문의 올린 시간

}