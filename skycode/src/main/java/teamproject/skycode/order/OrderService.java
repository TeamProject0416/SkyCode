package teamproject.skycode.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.skycode.login.MemberEntity;
import teamproject.skycode.login.MemberRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;


    public void saveTicketResult(OrderDto orderDto) {
        Order order = new Order();
        order.setGoingStart(orderDto.getGoingStart());
        order.setGoingArrive(orderDto.getGoingArrive());
        order.setComingStart(orderDto.getComingStart());
        order.setComingArrive(orderDto.getComingArrive());
        order.setGoingTime(orderDto.getGoingTime());
        order.setUserGrade(orderDto.getUserGrade());
        order.setComingTime(orderDto.getComingTime());
        order.setGoingPrice(orderDto.getGoingPrice());
        order.setComingPrice(orderDto.getComingPrice());
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setOrderDate(LocalDateTime.now());

        Optional<MemberEntity> memberEntityList = memberRepository.findById(orderDto.getMemberId());
        MemberEntity memberEntity = memberEntityList.get();
        order.setMemberEntity(memberEntity);

        orderRepository.save(order);
    }


}