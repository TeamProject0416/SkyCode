package teamproject.skycode.order;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.skycode.login.MemberEntity;
import teamproject.skycode.login.MemberRepository;
import teamproject.skycode.ticket.TicketEntity;
import teamproject.skycode.ticket.TicketFormDto;
import teamproject.skycode.ticket.TicketRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, MemberRepository memberRepository) {
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Order placeOrder(MemberEntity member, OrderDto orderDto) {
        Order order = new Order();
        order.setMemberEntity(member);
        order.setGoingStart(orderDto.getGoingStart());  // 가는 편 출발
        order.setGoingArrive(orderDto.getGoingArrive()); // 가는 편 도착
        order.setGoingTime(orderDto.getGoingTime());  // 날짜 + 시간
        order.setGoingPrice(orderDto.getGoingPrice());  // 총 금액
        order.setUserGrade(orderDto.getUserGrade());  // 선택 좌석 등급
        order.setComingStart(orderDto.getComingStart());  // 오는 편 출발
        order.setComingArrive(orderDto.getComingArrive());  // 오는 편 도착
        order.setComingTime(orderDto.getComingTime());  // 오는 편 날짜 + 시간
        order.setComingPrice(orderDto.getComingPrice());  // 오는 편 총 금액

        // 총 가격 계산
        int totalPrice = orderDto.getGoingPrice() + orderDto.getComingPrice();
        order.setTotalPrice(totalPrice);

        order.setOrderDate(LocalDateTime.now());

        // 주문 정보 저장
        order = orderRepository.save(order);

        return order;
    }

}