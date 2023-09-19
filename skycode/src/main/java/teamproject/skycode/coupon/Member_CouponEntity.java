package teamproject.skycode.coupon;


import lombok.Getter;
import lombok.Setter;
import teamproject.skycode.login.MemberEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Member_CouponEntity {
    @Id
    @Column(name = "memCoupon_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @Column
    private String couponCode;

    @Column
    private String memberEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private CouponEntity couponEntity;

}
