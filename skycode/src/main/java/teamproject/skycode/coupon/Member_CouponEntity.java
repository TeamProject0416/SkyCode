package teamproject.skycode.coupon;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Member_CouponEntity {
    @Id
    @Column(name = "MemCoupon_id")
    private  Long id;
}
