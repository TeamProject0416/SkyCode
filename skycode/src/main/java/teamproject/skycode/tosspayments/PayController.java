package teamproject.skycode.tosspayments;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import teamproject.skycode.login.MemberEntity;
import teamproject.skycode.login.MemberRepository;
import teamproject.skycode.order.Order;
import teamproject.skycode.order.OrderDto;
import teamproject.skycode.order.OrderRepository;
import teamproject.skycode.toss.PaymentReq;
import teamproject.skycode.toss.PaymentService;
import teamproject.skycode.toss.SimplePaymentResponse;

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
            @RequestParam(name = "amount") int amount
    , OrderDto orderDto) throws Exception {
        PaymentReq paymentReq = PaymentReq.make(paymentKey, orderId, amount);
        System.err.println(paymentReq);
        // 현재 인증된 사용자의 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.err.println(authentication);

        if (authentication != null) {
            System.err.println("rerereerer");
            String email = authentication.getName(); // 현재 로그인한 사용자의 닉네임
            MemberEntity memberEntity = memberRepository.findByEmail(email);
            if (memberEntity != null) {
                System.err.println("123123");
                int paymentAmount = paymentReq.getAmount();
//                int currentPoint = memberEntity.getPoint(); // int로 형변환
//                int updatedPoint = currentPoint + (int) paymentAmount; // int로 형변환하여 더하기

                Order order = new Order();
                System.err.println(order);
                order.setMemberEntity(memberEntity);;
                order.setGoingStart(orderDto.getGoingStart());
                order.setGoingArrive(orderDto.getGoingArrive());
                order.setComingStart(orderDto.getComingStart());
                order.setComingArrive(orderDto.getComingArrive());
                order.setGoingTime(orderDto.getGoingTime());
                order.setUserGrade(orderDto.getUserGrade());
                order.setComingTime(orderDto.getComingTime());
                order.setGoingPrice(orderDto.getGoingPrice());
                order.setComingPrice(orderDto.getComingPrice());
                order.setTotalPrice(paymentAmount);
                order.setOrderDate(LocalDateTime.now());
                orderRepository.save(order);
                System.err.println(order.getMemberEntity());
                System.err.println(order.getTotalPrice());

//                memberRepository.save(memberEntity); // 업데이트를 데이터베이스에 저장

                // 리다이렉트 응답을 반환합니다.
                // 결제 성공후 메인화면으로 이동
                HttpHeaders headers = new HttpHeaders();
                headers.add("Location", "/payment/success/end?amount=" + amount);
                return new ResponseEntity<>(headers, HttpStatus.FOUND);
            } else {
                System.err.println("serser");
                String failureMessage = "결제가 실패했습니다. 다시 시도해주세요.";
                // 리다이렉트하고 경고 메시지를 쿼리 문자열로 전달
                return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/payment/error?message=" + failureMessage).build();
            }
        }
        return ResponseEntity.ok().body(paymentService.paymentTest(paymentReq));
    }

}
