package teamproject.skycode.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import teamproject.skycode.constant.EventStatus;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class EventFormDto {
    private Long id;

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String eventTitle;

    private String content;

    private EventStatus eventStatus;

    private String eventTime; // "yyyy-MM-dd"

    private String miniImgName; // 미니 이미지 파일명
    private String miniOriImgName; // 미니 원본 이미지 파일명
    private String miniImgUrl; // 미니 이미지 조회 경로

    private String bigImgName; // 큰 이미지 파일명
    private String bigOriImgName; // 큰 원본 이미지 파일명
    private String bigImgUrl; // 큰 이미지 조회 경로

    private String couponCode;  // 등록할 쿠폰 코드

    private static ModelMapper modelMapper = new ModelMapper();

    public EventEntity createEvent() {
        return modelMapper.map(this, EventEntity.class);
    } // EventFormDto 객체를 기반으로 새로운 Event 객체를 생성
    // this 는 EventFormDto 객체 자체를 나타냅니다
    // EventFormDto 객체의 필드값을 가지고 Event 객체를 생성

    public static EventFormDto of(EventEntity event) {
        return modelMapper.map(event, EventFormDto.class);
    }
    // Event 객체에서 EventFormDto 객체로 매핑
}
