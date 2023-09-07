package teamproject.skycode.coupon;


import lombok.*;
import teamproject.skycode.event.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Getter
@Entity
@Table(name = "coupon")
@Setter
@ToString
public class CouponEntity extends BaseEntity {
    @Id
    @Column(name = "coupon_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 쿠폰 Id

    private String couponName; // 쿠폰 이름

    private Integer couponNum; // 초기 쿠폰 수

    @Column(name = "code", nullable = false, unique = true, updatable = false)
    private String code;  // TODO: 랜덤 코드, add index

    private Integer couponPrice; // 할인 금액

    private Integer couponRatio; // 할인 비율

    @Enumerated(EnumType.STRING)
    private CouponStatus couponStatus; // 쿠폰 상태

    private Integer maxPrice; //최대 할인 금액

    private Integer useMinPrice; // 최소 사용 금액

    private String couponStart; // 쿠폰 시작일 "yyyy-MM-dd"

    private String couponEnd; // 쿠폰 종료일 "yyyy-MM-dd"


    public Integer getCouponNum() {
        return couponNum;
    }

    public void setCouponNum() {
        this.couponNum = this.couponNum == 0 ? null : this.couponNum - 1;
    }

    public void updateEvent(CouponFormDto couponFormDto) {
        this.couponName = couponFormDto.getCouponName();
        this.couponNum = couponFormDto.getCouponNum();
        this.couponPrice = couponFormDto.getMaxPrice();
        this.couponRatio = couponFormDto.getCouponRatio();
        this.couponStatus = couponFormDto.getCouponStatus();
        this.maxPrice = couponFormDto.getMaxPrice();
        this.useMinPrice = couponFormDto.getUseMinPrice();
        this.couponStart = couponFormDto.getCouponStart();
        this.couponEnd = couponFormDto.getCouponEnd();
    }
}
