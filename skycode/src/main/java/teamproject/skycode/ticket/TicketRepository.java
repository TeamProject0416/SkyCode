package teamproject.skycode.ticket;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import teamproject.skycode.constant.TicketCountry;
import teamproject.skycode.coupon.CouponEntity;
import teamproject.skycode.event.EventEntity;
import teamproject.skycode.review.ReviewEntity;

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



    //    @Query("SELECT *" +
//            "FROM tickets" +
//            "WHERE destination = '도착지값' AND departure_time >= '시간값';")
//    가는 편 검색 쿼리
    @Query("SELECT t FROM TicketEntity t " +
           "WHERE (t.ticketStart = :startValue AND t.ticketEnd = :arriveValue AND t.ticketStartDate = :startDate) " +
           "AND (t.firstStatus = 'SELL' OR t.businessStatus = 'SELL' OR t.economyStatus = 'SELL') ")
    List<TicketEntity> findByGoingTicket(
            @Param("startValue") TicketCountry startValue,
            @Param("arriveValue") TicketCountry arriveValue,
            @Param("startDate") String startDate
    );

//    오는편 검색 쿼리
    @Query("SELECT t FROM TicketEntity t " +
           "WHERE (t.ticketStart = :arriveValue AND t.ticketEnd = :startValue AND t.ticketStartDate = :endDate) " +
           "AND (t.firstStatus = 'SELL' OR t.businessStatus = 'SELL' OR t.economyStatus = 'SELL') ")
    List<TicketEntity> findByComingTicket(
            @Param("arriveValue") TicketCountry arriveValue,
            @Param("startValue") TicketCountry startValue,
            @Param("endDate") String endDate
    );

    Integer findByFirstPrice(Integer firstPrice);
    Integer findByBusinessPrice(Integer businessPrice);
    Integer findByEconomyPrice(Integer economyPrice);


}
