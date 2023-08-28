package teamproject.skycode.event;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
public class Event {
    private Long id; // 이벤트 Id
    private String eventTitle; // 이벤트 제목
    private String nickName; // 작성자 닉네임
    private String content; // 이벤트 내용
    private LocalDateTime regTime; // 작성시간

}
