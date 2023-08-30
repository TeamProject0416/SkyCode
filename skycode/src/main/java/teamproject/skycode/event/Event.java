package teamproject.skycode.event;

import lombok.*;
import teamproject.skycode.constant.EventStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "event")
@Setter
@ToString
public class Event extends BaseEntity {
    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 이벤트 Id
    @Column(nullable = false, length = 50)
    private String eventTitle; // 이벤트 제목
    @Lob
    @Column(nullable = false)
    private String content; // 이벤트 내용

    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus; // 이벤트 진행 상태

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventImg> eventImg;

    public void updateEvent(EventFormDto eventFormDto) {
        this.eventTitle = eventFormDto.getEventTitle();
        this.content = eventFormDto.getContent();
        this.eventStatus = eventFormDto.getEventStatus();
    }
    // EventFormDto 로부터 가져온 정보를 사용하여 Event 엔티티의 필드를 업데이트


}
