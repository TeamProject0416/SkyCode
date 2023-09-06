package teamproject.skycode.coupon;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
public class CouponFormDto {

    private Long id; // 쿠폰 Id

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String couponName; // 쿠폰 이름

    private double couponPrice; // 할인 금액

    private double couponRatio; // 할인 비율

    private CouponStatus couponStatus; // 쿠폰 상태

    private double maxPrice; //최대 할인 금액

    private double useMinPrice; // 최소 사용 금액

    private String couponTime; //"yyyy-MM-dd"

}
