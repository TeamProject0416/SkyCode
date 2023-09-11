package teamproject.skycode.ticket;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import teamproject.skycode.coupon.CouponEntity;
import teamproject.skycode.event.EventEntity;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    @Query("SELECT t FROM TicketEntity t WHERE t.firstStatus = 'SELL' " +
            "OR t.businessStatus = 'SELL' " +
            "OR t.economyStatus = 'SELL' " +
            "ORDER BY t.regTime DESC")
    List<TicketEntity> findBySELL();
    // 판매중인 것만 가져오기
}
