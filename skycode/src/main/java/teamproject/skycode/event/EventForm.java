package teamproject.skycode.event;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
public class EventForm {
    private Long id;
    private String reviewTitle;
    private String nickName;
    private String body;
    private LocalDateTime regTime;

    public Event toEntity() {
        return new Event(id, reviewTitle, nickName, body, regTime);
    }
}
