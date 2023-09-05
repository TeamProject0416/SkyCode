package teamproject.skycode.event;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Event {
    @Id
    private Long id; // 이벤트 Id
    private String eventTitle; // 이벤트 제목
    private String nickName; // 작성자 닉네임
    private String content; // 이벤트 내용
    private LocalDateTime regTime; // 작성시간

}
