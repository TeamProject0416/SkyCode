package teamproject.skycode.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import teamproject.skycode.login.MemberEntity;
import teamproject.skycode.login.MemberRepository;
import teamproject.skycode.review.ReviewEntity;
import teamproject.skycode.review.ReviewRepository;
import teamproject.skycode.ticket.TicketEntity;
import teamproject.skycode.ticket.TicketRepository;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final TicketRepository ticketRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    @GetMapping(value = "/list") // 진행중인 티켓 목록 보이기
    public String orderPage(Model model, Principal principal) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum",reviewNum);
        }

        List<TicketEntity> tickets = ticketRepository.findBySELL();
        model.addAttribute("tickets", tickets);
        return "order/ticketOrderPage";
    }


}
