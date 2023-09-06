package teamproject.skycode.coupon;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Getter
@Entity
@Table(name = "coupon")
@Setter
@ToString
public class CouponEntity {
    @Id
    @Column(name = "coupon_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 쿠폰 Id

    private String couponName; // 쿠폰 이름

    private Integer num; // 초기 쿠폰 수

    @Pattern(regexp = ValidationRegex.COUPONCODE)
    @Column(name = "code", nullable = false, unique = true, updatable = false)
    private String code;  // TODO: 랜덤 코드, add index
    private double couponPrice; // 할인 금액

    private double couponRatio; // 할인 비율

    @Enumerated(EnumType.STRING)
    private CouponStatus couponStatus; // 쿠폰 상태

    private double maxPrice; //최대 할인 금액

    private double useMinPrice; // 최소 사용 금액

    private String couponStart; // 쿠폰 시작일 "yyyy-MM-dd"

    private String couponEnd; // 쿠폰 종료일 "yyyy-MM-dd"


    public Integer getNum() {
        return num;
    }

    public void setNum() {
        this.num = this.num == 0 ? null : this.num - 1;
    }

}
