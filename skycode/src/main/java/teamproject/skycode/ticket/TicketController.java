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
import teamproject.skycode.constant.TicketCountry;
import teamproject.skycode.login.MemberEntity;
import teamproject.skycode.login.MemberRepository;
import teamproject.skycode.order.Order;
import teamproject.skycode.order.OrderDto;
import teamproject.skycode.order.OrderService;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final OrderService orderService;
    private final MemberRepository memberRepository;

    @GetMapping(value = {"/list", "/list/{page}"}) // 진행 페이지
    public String skyTicket(@PathVariable(name = "page", required = false) Integer page,
                            Model model, Principal principal) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum",reviewNum);
        }

        int pageSize = 3; // 페이지당 표시할 이벤트 수
        Pageable pageable = PageRequest.of(page != null ? page : 0, pageSize, Sort.by("id").descending());



        // EventStatus.ONGOING 값을 사용하여 데이터 조회
        Page<TicketEntity> ticketPage = ticketService.getTicketPage(pageable);

        model.addAttribute("tickets", ticketPage); // Page 객체를 그대로 넘김
        model.addAttribute("maxPage", 5); // 페이지당 표시할 최대 페이지 수

        return "/ticket/ticketList";
    }

    @GetMapping(value = "/new") // 티켓 등록
    public String newTicketForm(Model model, Principal principal) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum",reviewNum);
        }

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

    @GetMapping(value = "/{ticketId}/edit") // 티켓 수정폼
    public String ticketEdit(@PathVariable("ticketId") Long ticketId, Model model, Principal principal) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum",reviewNum);
        }

        TicketFormDto ticketFormDto = ticketService.getTicketDtl(ticketId);
        model.addAttribute("ticketFormDto", ticketFormDto);
        return "ticket/ticketForm";
    }

    @PostMapping(value = "/update") // 티켓 수정
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

    @GetMapping("/{ticketId}/delete") // 티켓 삭제
    public String deleteTicket(@PathVariable("ticketId") Long ticketId,Model model, Principal principal) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum",reviewNum);
        }

        // 이벤트 삭제 로직을 구현
        ticketService.deleteTicket(ticketId);

        // 삭제 후 리다이렉션할 URL을 반환
        return "redirect:/ticket/list";
    }

    @PostMapping(value = "/ticketSearch")
    public String searchTicket(
            @RequestParam(name = "startValue") TicketCountry startValue,
            @RequestParam(name = "arriveValue") TicketCountry arriveValue,
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate,
            @RequestParam(name = "adultNum") int adultNum,
            @RequestParam(name = "teenagerNum") int teenagerNum,
            @RequestParam(name = "childNum") int childNum,
            @RequestParam(name = "seatGrade") String seatGrade,
            TicketFormDto ticketFormDto,
            Model model) {

        Integer totalNum = adultNum + teenagerNum + childNum;
        String userGrade = seatGrade;

        // 모델에 데이터를 추가하고, 결과 페이지로 이동합니다.
        List<TicketEntity> resultGoingList = ticketService.ticketGoinhEntityList(startValue, arriveValue, startDate);
        List<TicketEntity> resultComingList = ticketService.ticketComingEntityList(arriveValue, startValue, endDate);



//        뷰에 모델 추가
//        model.addAttribute("userSelectGrade", userSelectGrade);
        model.addAttribute("totalNum",totalNum);
        model.addAttribute("userSeatGrade",userGrade);
        model.addAttribute("goingTickets", resultGoingList); // 가는 편 리스트
        model.addAttribute("comingTickets", resultComingList); // 오는 편 리스트
        return "ticket/ticketSearch"; // 결과를 보여줄 뷰 페이지의 경로를 반환
    }

    @PostMapping(value = "/payment")
    public String Payments(
        @RequestParam(name = "goingResultTotalPrice") int goingResultTotalPrice,
        @RequestParam(name = "goingResultStart") String goingResultStart,
        @RequestParam(name = "goingResultArrive") String goingResultArrive,
        @RequestParam(name = "goingStartArriveTime") String goingStartArriveTime,
        @RequestParam(name = "goingUserSelectGrade") String goingUserSelectGrade,
        @RequestParam(name = "comingStartArriveTime") String comingStartArriveTime,
        @RequestParam(name = "comingResultTotalPrice") int comingResultTotalPrice,
        Principal principal,
        Model model
    ) {
        int goingPrice = goingResultTotalPrice;
        int comingPrice = comingResultTotalPrice;
        int resultPrice = goingPrice + comingPrice;

        OrderDto orderDto = new OrderDto();
        orderDto.setGoingStart(goingResultStart);
        orderDto.setGoingArrive(goingResultArrive);
        orderDto.setGoingTime(goingStartArriveTime);
        orderDto.setGoingPrice(goingResultTotalPrice);

        orderDto.setUserGrade(goingUserSelectGrade);

        orderDto.setComingStart(goingResultArrive);
        orderDto.setComingArrive(goingResultStart);
        orderDto.setComingTime(comingStartArriveTime);
        orderDto.setComingPrice(comingResultTotalPrice);
        orderDto.setTotalPrice(resultPrice);

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity memberEntity = memberRepository.findByEmail(user);
            orderDto.setEmail(user);
            orderDto.setMemberId(memberEntity.getId());
            orderDto.setMemberName(memberEntity.getName());
        }

//        orderService.saveTicketResult(orderDto);

        model.addAttribute("orderDto", orderDto);
        model.addAttribute("resultPrice", resultPrice);

        return "toss/pay";
    }
}
