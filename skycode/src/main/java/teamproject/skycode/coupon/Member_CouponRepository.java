package teamproject.skycode.coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Member_CouponRepository extends JpaRepository<Member_CouponEntity, Long> {

    List<Member_CouponEntity> findByMemberEmail(String userEmail);
}
