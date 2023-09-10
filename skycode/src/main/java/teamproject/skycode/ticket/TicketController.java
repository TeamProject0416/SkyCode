package teamproject.skycode.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping(value = {"/list", "/list/{page}"}) // 진행 페이지
    public String skyTicket(@PathVariable(name = "page", required = false) Integer page, TicketEntity ticketEntity , Model model) {
        int pageSize = 3; // 페이지당 표시할 이벤트 수
        Pageable pageable = PageRequest.of(page != null ? page : 0, pageSize, Sort.by("id").descending());

        // EventStatus.ONGOING 값을 사용하여 데이터 조회
        Page<TicketEntity> ticketPage = ticketService.getTicketPage(pageable);

        model.addAttribute("tickets", ticketPage); // Page 객체를 그대로 넘김
        model.addAttribute("maxPage", 5); // 페이지당 표시할 최대 페이지 수

        return "/ticket/ticketList";
    }

    @GetMapping(value = "/new") // 티켓 등록
    public String newTicketForm(Model model) {
        model.addAttribute("ticketFormDto", new TicketFormDto());
        return "ticket/ticketForm";
    }

    @PostMapping(value = "/new") // 티켓 등록
    public String createTicket(@Valid TicketFormDto ticketFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "ticket/ticketForm";
        }
        try {
            ticketService.ticketSave(ticketFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "티켓 등록 중 에러가 발생하였습니다");
            return "ticket/ticketForm";
        }
        return "redirect:/ticket/list";
    }

    @GetMapping(value = "/{ticketId}/edit") // 쿠폰 수정폼
    public String ticketEdit(@PathVariable("ticketId") Long ticketId, Model model) {
        TicketFormDto ticketFormDto = ticketService.getTicketDtl(ticketId);
        model.addAttribute("ticketFormDto", ticketFormDto);
        return "ticket/ticketForm";
    }

    @PostMapping(value = "/update") // 이벤트 수정
    public String ticketUpdate(@Valid TicketFormDto ticketFormDto, BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "ticket/ticketForm";
        }
        try {
            ticketService.updateTicket(ticketFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "이벤트 등록 중 에러가 발생하였습니다");
            return "ticket/ticketForm";
        }
        return "redirect:/ticket/list";
    }

    @GetMapping("/{ticketId}/delete") // 이벤트 삭제
    public String deleteTicket(@PathVariable("ticketId") Long ticketId) {
        // 이벤트 삭제 로직을 구현
        ticketService.deleteTicket(ticketId);

        // 삭제 후 리다이렉션할 URL을 반환
        return "redirect:/ticket/list";
    }


}
