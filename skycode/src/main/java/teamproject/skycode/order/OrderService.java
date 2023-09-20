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
        System.err.println(goingStart);
        String goingArrive = orderDto.getGoingArrive();
        System.err.println(goingArrive);
        String goingTime = orderDto.getGoingTime();
        System.err.println(goingTime);
        Integer goingPrice = orderDto.getGoingPrice();
        System.err.println(goingPrice);
        String userGrade = orderDto.getUserGrade();
        System.err.println(userGrade);
        String comingStart = orderDto.getComingStart();
        System.err.println(comingStart);
        String comingArrive = orderDto.getComingArrive();
        System.err.println(comingArrive);
        String comingTime = orderDto.getComingTime();
        System.err.println(comingTime);
        Integer comingPrice = orderDto.getComingPrice();
        System.err.println(comingPrice);
        Integer totalPrice = orderDto.getTotalPrice();
        System.err.println(totalPrice);

        System.err.println("아이디 찾기이" + orderDto.getMemberId());

        MemberEntity memberEntity = memberRepository.findById(orderDto.getMemberId())
                .orElseThrow(EntityNotFoundException::new);

        System.err.println("memberEntity:" + memberEntity);
        // 중복 체크를 위한 데이터를 사용하여 중복 주문을 찾습니다.
        Order existingOrder = orderRepository.findByDuplicateFields(
                goingStart, goingArrive, goingTime, goingPrice, userGrade,
                comingStart, comingArrive, comingTime, comingPrice, totalPrice,
                memberEntity
        );
        System.err.println("existingOrder:" + existingOrder);

        // 중복 주문이 없을 경우에만 주문을 저장합니다.
        if (existingOrder == null) {
            System.err.println("중복값이 없어용!");
            Order order = new Order();
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
            System.err.println("order: " + order);

            orderRepository.save(order);
        } else {
            // 멤버를 찾을 수 없는 경우에 대한 처리
            throw new EntityNotFoundException("멤버를 찾을 수 없습니다.");
        }
    }
}


