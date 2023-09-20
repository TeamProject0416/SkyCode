package teamproject.skycode.toss;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import teamproject.skycode.constant.OrderStatus;
import teamproject.skycode.login.MemberEntity;
import teamproject.skycode.login.MemberRepository;
import teamproject.skycode.order.OrderEntity;
import teamproject.skycode.order.OrderDto;
import teamproject.skycode.order.OrderRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PayController {
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final PaymentService paymentService;


    @GetMapping(value = "/toss")
    public String success() {
        return "toss/success";
    }

    @GetMapping("/success")
    public ResponseEntity<SimplePaymentResponse> getPaymentSuccessTest(
            @RequestParam(name = "paymentKey") String paymentKey,
            @RequestParam(name = "orderId") String orderId,
            @RequestParam(name = "amount") int amount,
            OrderDto orderDto) throws Exception {
        PaymentReq paymentReq = PaymentReq.make(paymentKey, orderId, amount);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            String email = authentication.getName(); // 현재 로그인한 사용자의 닉네임
            MemberEntity memberEntity = memberRepository.findByEmail(email);
            if (memberEntity != null) {
                int paymentAmount = paymentReq.getAmount();

                OrderEntity order = orderRepository.findByTotalPriceAndOrderStatusAndMemberEntity(paymentAmount, OrderStatus.ONGOING, memberEntity);
                System.err.println("order: " + order);

                if (order != null) {
                    order.setTotalPrice(paymentAmount);
                    order.setOrderDate(LocalDateTime.now());
                    order.setOrderStatus(OrderStatus.BUY);
                    order.setGoingStart(order.getGoingStart());
                    order.setGoingArrive(order.getGoingArrive());
                    order.setGoingTime(order.getGoingTime());
                    order.setGoingPrice(order.getGoingPrice());
                    order.setUserGrade(order.getUserGrade());
                    order.setMemberEntity(order.getMemberEntity());
                    System.err.println("이부분부터");

                    orderRepository.save(order);
                } else {
                    // 주문을 찾지 못한 경우에 대한 처리
                    throw new EntityNotFoundException("주문을 찾을 수 없습니다.");
                }

                // 리다이렉트 응답을 반환합니다.
                // 결제 성공후 메인화면으로 이동
                HttpHeaders headers = new HttpHeaders();
                headers.add("Location", "/");
                return new ResponseEntity<>(headers, HttpStatus.FOUND);
            } else {
                String failureMessage = "결제가 실패했습니다. 다시 시도해주세요.";
                // 리다이렉트하고 경고 메시지를 쿼리 문자열로 전달
                return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/payment/error?message=" + failureMessage).build();
            }
        }
        return ResponseEntity.ok().body(paymentService.paymentTest(paymentReq));
    }


}
