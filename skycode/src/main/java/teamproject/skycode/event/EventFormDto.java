package teamproject.skycode.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import teamproject.skycode.constant.EventStatus;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class EventFormDto {
    private Long id;

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String eventTitle;

    private String content;

    private EventStatus eventStatus;

    private List<EventImgDto> eventImgDtoList = new ArrayList<>();
    private List<Long> eventImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Event createEvent() {
        return modelMapper.map(this, Event.class);
    } // EventFormDto 객체를 기반으로 새로운 Event 객체를 생성
    // this 는 EventFormDto 객체 자체를 나타냅니다
    // EventFormDto 객체의 필드값을 가지고 Event 객체를 생성

    public static EventFormDto of(Event event) {
        return modelMapper.map(event, EventFormDto.class);
    }
    // Item 객체에서 ItemFormDto 객체로 매핑
}
