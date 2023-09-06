package teamproject.skycode.coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import teamproject.skycode.event.EventEntity;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, Long> {

    @Query("SELECT c FROM CouponEntity c WHERE c.couponStatus = 'ONGOING' ORDER BY c.regTime DESC")
    List<CouponEntity> findByONGOING();
    // 이벤트 상태가 진행중인 것만 가져오기

    @Query("SELECT c FROM CouponEntity c WHERE c.couponStatus = 'END' ORDER BY c.regTime DESC")
    List<CouponEntity> findByEND();
    // 이벤트 상태가 종료된 것만 가져오기
}
