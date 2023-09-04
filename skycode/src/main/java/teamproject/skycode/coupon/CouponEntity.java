package teamproject.skycode.coupon;

import teamproject.skycode.constant.CouponStatus;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
public class CouponEntity {
    @Id
    @Column(name = "coupon_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 쿠폰 Id

    private String couponName; // 쿠폰 이름

    @Pattern(regexp = ValidationRegex.COUPONCODE)
    @Column(name = "code", nullable = false, unique = true, updatable = false)
    private String code;  // TODO: 랜덤 코드, add index
    private double couponPrice; // 할인 금액

    private double couponRatio; // 할인 비율

    @Enumerated(EnumType.STRING)
    private CouponStatus couponStatus; // 쿠폰 상태

    private String couponTime; //"yyyy-MM-dd"

    private double maxPrice; //최대 할인 금액

    private double useMinPrice; // 최소사용금액

}
