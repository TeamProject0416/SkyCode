package teamproject.skycode.point;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.skycode.coupon.Member_CouponEntity;

import java.util.List;

@Repository
public interface Member_PointRepository extends JpaRepository<Member_PointEntity, Long> {

    List<Member_PointEntity> findByMemberEmail(String userEmail);
}
