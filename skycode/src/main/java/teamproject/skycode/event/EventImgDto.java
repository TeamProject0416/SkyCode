package teamproject.skycode.event;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EventImgDto {
    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static EventImgDto of(EventImg eventImg){
        return modelMapper.map(eventImg,EventImgDto.class);
    }
    // eventImg 엔티티의 객체를 파라미터로 받아서 eventImg 객체의 자료형과 이름이 같으면 EventImgDto 로 값을 복사해서 반환한다

}
