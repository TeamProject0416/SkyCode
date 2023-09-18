package teamproject.skycode.point;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class PointFormDto {

    private Long id; // 쿠폰 Id
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String pointName; // 포인트 이름

    @NotNull(message = "지급 포인트는 필수 입력 값입니다.")
    private Integer pointNum; // 포인트

    private static ModelMapper modelMapper = new ModelMapper();
    public PointEntity createPoint() {
        return modelMapper.map(this, PointEntity.class);
    }

    public static PointFormDto of(PointEntity point) {
        return modelMapper.map(point, PointFormDto.class);
    }

}
