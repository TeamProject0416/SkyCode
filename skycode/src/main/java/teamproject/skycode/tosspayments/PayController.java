package teamproject.skycode.tosspayments;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tosspayments")
public class PayController {

    @GetMapping(value = "/pay")
    public String pay() {
        return "tosspayments/pay";
    }

    @PostMapping(value = "/tossPayments")
    public String tossPayments() {
        return "tosspayments/tossPayments";
    }
}
