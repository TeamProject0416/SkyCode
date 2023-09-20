package teamproject.skycode.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.skycode.constant.OrderStatus;
import teamproject.skycode.login.MemberEntity;
import teamproject.skycode.login.MemberRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;


//    public void saveTicketResult(OrderDto orderDto) {
//        Order order = new Order();
//        order.setGoingStart(orderDto.getGoingStart());
//        order.setGoingArrive(orderDto.getGoingArrive());
//        order.setComingStart(orderDto.getComingStart());
//        order.setComingArrive(orderDto.getComingArrive());
//        order.setGoingTime(orderDto.getGoingTime());
//        order.setUserGrade(orderDto.getUserGrade());
//        order.setComingTime(orderDto.getComingTime());
//        order.setGoingPrice(orderDto.getGoingPrice());
//        order.setComingPrice(orderDto.getComingPrice());
//        order.setTotalPrice(orderDto.getTotalPrice());
//        order.setOrderStatus(orderDto.getOrderStatus());
//        order.setOrderDate(LocalDateTime.now());
//
//        Optional<MemberEntity> memberEntityList = memberRepository.findById(orderDto.getMemberId());
//        MemberEntity memberEntity = memberEntityList.get();
//        order.setMemberEntity(memberEntity);
//
//        orderRepository.save(order);
//    }


    public void saveTicketResult(OrderDto orderDto) {
        System.err.println("여기는?.?");
        // 중복 체크를 위해 필요한 데이터를 OrderDto에서 추출합니다.

        String goingStart = orderDto.getGoingStart();
        String goingArrive = orderDto.getGoingArrive();
        String goingTime = orderDto.getGoingTime();
        Integer goingPrice = orderDto.getGoingPrice();
        String userGrade = orderDto.getUserGrade();
        String comingStart = orderDto.getComingStart();
        String comingArrive = orderDto.getComingArrive();
        String comingTime = orderDto.getComingTime();
        Integer comingPrice = orderDto.getComingPrice();
        Integer totalPrice = orderDto.getTotalPrice();
        OrderStatus status = orderDto.getOrderStatus();
        System.err.println("123123"+status);


        MemberEntity memberEntity = memberRepository.findById(orderDto.getMemberId())
                .orElseThrow(EntityNotFoundException::new);

        System.err.println("memberEntity:" + memberEntity);
        // 중복 체크를 위한 데이터를 사용하여 중복 주문을 찾습니다.
        OrderEntity existingOrder = orderRepository.findByDuplicateFields(
                goingStart, goingArrive, goingTime, goingPrice, userGrade,
                comingStart, comingArrive, comingTime, comingPrice, totalPrice,
                memberEntity
        );



        // 중복 주문이 없을 경우에만 주문을 저장합니다.
        if (existingOrder == null) {
            System.err.println("중복값이 없어용!");
            OrderEntity order = new OrderEntity();
            order.setGoingStart(goingStart);
            order.setGoingArrive(goingArrive);
            order.setComingStart(comingStart);
            order.setComingArrive(comingArrive);
            order.setGoingTime(goingTime);
            order.setUserGrade(userGrade);
            order.setComingTime(comingTime);
            order.setGoingPrice(goingPrice);
            order.setComingPrice(comingPrice);
            order.setTotalPrice(totalPrice);
            order.setOrderStatus(OrderStatus.ONGOING);
            order.setOrderDate(LocalDateTime.now());
            order.setMemberEntity(memberEntity);

            orderRepository.save(order);
        } else {
//            중복값이 있으면
            orderRepository.save(existingOrder);

        }
    }
}


