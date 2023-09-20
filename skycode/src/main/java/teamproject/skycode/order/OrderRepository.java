package teamproject.skycode.order;

import com.querydsl.core.types.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import teamproject.skycode.constant.OrderStatus;
import teamproject.skycode.login.MemberEntity;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT o FROM OrderEntity o WHERE o.goingStart = :goingStart " +
            "AND o.goingArrive = :goingArrive AND o.goingTime = :goingTime " +
            "AND o.goingPrice = :goingPrice AND o.userGrade = :userGrade " +
            "AND o.comingStart = :comingStart AND o.comingArrive = :comingArrive " +
            "AND o.comingTime = :comingTime AND o.comingPrice = :comingPrice " +
            "AND o.totalPrice = :totalPrice AND o.memberEntity = :memberEntity")
    OrderEntity findByDuplicateFields(
            @Param("goingStart") String goingStart,
            @Param("goingArrive") String goingArrive,
            @Param("goingTime") String goingTime,
            @Param("goingPrice") Integer goingPrice,
            @Param("userGrade") String userGrade,
            @Param("comingStart") String comingStart,
            @Param("comingArrive") String comingArrive,
            @Param("comingTime") String comingTime,
            @Param("comingPrice") Integer comingPrice,
            @Param("totalPrice") Integer totalPrice,
            @Param("memberEntity") MemberEntity memberEntity
    );

    OrderEntity findByTotalPriceAndOrderStatusAndMemberEntity(int paymentAmount, OrderStatus orderStatus, MemberEntity memberEntity);

    List<OrderEntity> findByMemberEntityId(Long id);
}
