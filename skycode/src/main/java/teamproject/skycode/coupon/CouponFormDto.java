package teamproject.skycode.coupon;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class CouponFormDto {
    private Long id; // 쿠폰 Id
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String couponName; // 쿠폰 이름
    private Integer num; // 초기 쿠폰 수

    private double couponPrice; // 할인 금액
    private double couponRatio; // 할인 비율

    private CouponStatus couponStatus; // 쿠폰 상태

    private double maxPrice; //최대 할인 금액
    private double useMinPrice; // 최소 사용 금액

    private String couponStart; // 쿠폰 시작일 "yyyy-MM-dd"
    private String couponEnd; // 쿠폰 종료일 "yyyy-MM-dd"

}
