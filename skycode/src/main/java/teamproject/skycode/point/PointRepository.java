package teamproject.skycode.point;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import teamproject.skycode.constant.EventStatus;
import teamproject.skycode.coupon.CouponEntity;
import teamproject.skycode.event.EventEntity;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<PointEntity, Long> {

    List<PointEntity> findAllByOrderByRegTimeDesc();

    PointEntity findByPointName(String pointName);

}
