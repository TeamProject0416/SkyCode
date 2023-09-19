package teamproject.skycode.coupon;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import teamproject.skycode.constant.CouponStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class CouponFormDto {

    private Long id; // 쿠폰 Id
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String couponName; // 쿠폰 이름

    @NotNull(message = "수량은 필수 입력 값입니다.")
    private Integer couponNum; // 초기 쿠폰 수

    private Integer couponPrice; // 할인 금액
    private Integer couponRatio; // 할인 비율

    private CouponStatus couponStatus; // 쿠폰 상태

    private Integer maxPrice; //최대 할인 금액

    @NotNull(message = "최소 사용금액은 필수 입력 값입니다.")
    private Integer useMinPrice; // 최소 사용 금액

    @NotBlank(message = "쿠폰 시작 기간은 필수 입력 값입니다.")
    private String couponStart; // 쿠폰 시작일 "yyyy-MM-dd"
    @NotBlank(message = "쿠폰 종료 기간은 필수 입력 값입니다.")
    private String couponEnd; // 쿠폰 종료일 "yyyy-MM-dd"

    private static ModelMapper modelMapper = new ModelMapper();
    public CouponEntity createCoupon() {
        return modelMapper.map(this, CouponEntity.class);
    }

    public static CouponFormDto of(CouponEntity coupon) {
        return modelMapper.map(coupon, CouponFormDto.class);
    }

}
