package teamproject.skycode.event;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "event")
@Setter
@ToString
public class EventEntity extends BaseEntity {
    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 이벤트 Id

    @Column(nullable = false)
    private String eventTitle; // 이벤트 제목

    @Lob
    @Column(nullable = false)
    private String content; // 이벤트 내용

    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus; // 이벤트 진행 상태

    private String miniImgName; // 미니 이미지 파일명

    private String miniOriImgName; // 미니 원본 이미지 파일명

    private String miniImgUrl; // 미니 이미지 조회 경로

    private String bigImgName; // 큰 이미지 파일명

    private String bigOriImgName; // 큰 원본 이미지 파일명

    private String bigImgUrl; // 큰 이미지 조회 경로

    private String eventTime; // "yyyy-MM-dd"

    public void updateEventImg(String miniImgName, String miniOriImgName, String miniImgUrl,
                               String bigImgName, String bigOriImgName, String bigImgUrl) {
        this.miniImgName = miniImgName;
        this.miniOriImgName = miniOriImgName;
        this.miniImgUrl = miniImgUrl;
        this.bigImgName = bigImgName;
        this.bigOriImgName = bigOriImgName;
        this.bigImgUrl = bigImgUrl;
    }

    public void updateEvent(EventFormDto eventFormDto) {
        this.eventTitle = eventFormDto.getEventTitle();
        this.content = eventFormDto.getContent();
        this.eventStatus = eventFormDto.getEventStatus();

        this.miniImgName = eventFormDto.getMiniImgName();
        this.miniOriImgName = eventFormDto.getMiniOriImgName();
        this.miniImgUrl = eventFormDto.getMiniImgUrl();

        this.bigImgName = eventFormDto.getBigImgName();
        this.bigOriImgName = eventFormDto.getBigOriImgName();
        this.bigImgUrl = eventFormDto.getBigImgUrl();
    }




}
