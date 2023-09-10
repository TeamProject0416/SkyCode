package teamproject.skycode.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import teamproject.skycode.coupon.CouponEntity;
import teamproject.skycode.coupon.CouponRepository;
import teamproject.skycode.ticket.TicketEntity;
import teamproject.skycode.ticket.TicketFormDto;
import teamproject.skycode.ticket.TicketRepository;
import teamproject.skycode.ticket.TicketService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final TicketService ticketService;
    private final TicketRepository ticketRepository;

    @GetMapping(value = "/list") // 진행중인 쿠폰 목록 보이기
    public String orderPage(Model model) {
        List<TicketEntity> tickets = ticketRepository.findBySELL();
        model.addAttribute("tickets", tickets);
        return "order/ticketOrderPage";
    }


}
