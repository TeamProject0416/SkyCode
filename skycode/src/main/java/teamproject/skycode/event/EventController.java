package teamproject.skycode.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teamproject.skycode.constant.ActionType;
import teamproject.skycode.constant.EventStatus;
import teamproject.skycode.constant.Role;
import teamproject.skycode.coupon.Member_CouponEntity;
import teamproject.skycode.coupon.Member_CouponRepository;
import teamproject.skycode.login.MemberEntity;
import teamproject.skycode.login.MemberRepository;
import teamproject.skycode.news.inquiry.Inquiry;
import teamproject.skycode.news.inquiry.InquiryRepository;
import teamproject.skycode.point.PointHistoryEntity;
import teamproject.skycode.point.PointHistoryRepository;
import teamproject.skycode.review.ReviewEntity;
import teamproject.skycode.review.ReviewRepository;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final InquiryRepository inquiryRepository;
    private final Member_CouponRepository memberCouponRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @GetMapping(value = {"/ongoing", "/ongoing/{page}"}) // 진행 페이지
    public String ongoingEvent(@PathVariable(name = "page", required = false) Integer page,
                               Principal principal, Model model) {

        int pageSize = 3; // 페이지당 표시할 이벤트 수
        Pageable pageable = PageRequest.of(page != null ? page : 0, pageSize);

        // EventStatus.ONGOING 값을 사용하여 데이터 조회
        Page<EventEntity> eventPage = eventService.getEventPage(EventStatus.ONGOING, pageable);

        model.addAttribute("events", eventPage); // Page 객체를 그대로 넘김
        model.addAttribute("maxPage", 5); // 페이지당 표시할 최대 페이지 수

        // 유저 로그인 모달 함수
        populateAdminModel(model, principal);

        return "/event/eventongoing";
    }

    @GetMapping(value = {"/end", "/end/{page}"}) // 종료 페이지
    public String endEvent(@PathVariable(name = "page", required = false) Integer page,
                           Principal principal, Model model) {

        int pageSize = 3; // 페이지당 표시할 이벤트 수
        Pageable pageable = PageRequest.of(page != null ? page : 0, pageSize);

        // EventStatus.ONGOING 값을 사용하여 데이터 조회
        Page<EventEntity> eventPage = eventService.getEventPage(EventStatus.END, pageable);

        model.addAttribute("events", eventPage); // Page 객체를 그대로 넘김
        model.addAttribute("maxPage", 5); // 페이지당 표시할 최대 페이지 수

        // 유저 로그인 모달 함수
        populateAdminModel(model, principal);
        return "/event/eventend";
    }

    @GetMapping(value = {"/winner", "/winner/{page}"}) // 당첨자 페이지
    public String eventWinner(@PathVariable(name = "page", required = false) Integer page,
                              Principal principal, Model model) {
        int pageSize = 3; // 페이지당 표시할 이벤트 수
        Pageable pageable = PageRequest.of(page != null ? page : 0, pageSize);

        // EventStatus.ONGOING 값을 사용하여 데이터 조회
        Page<EventEntity> eventPage = eventService.getEventPage(EventStatus.WINNER, pageable);

        model.addAttribute("events", eventPage); // Page 객체를 그대로 넘김
        model.addAttribute("maxPage", 5); // 페이지당 표시할 최대 페이지 수

        // 유저 로그인 모달 함수
        populateAdminModel(model, principal);
        return "/event/eventwinner";
    }

    @GetMapping(value = "/new") // 이벤트 등록
    public String newEventForm(Model model, Principal principal) {

        // 유저 로그인 모달 함수
        populateAdminModel(model, principal);
        model.addAttribute("eventFormDto", new EventFormDto());
        return "event/eventForm";
    }

    @PostMapping(value = "/new") // 이벤트 등록
    public String createEvent(@Valid EventFormDto eventFormDto, BindingResult bindingResult, Model model,
                              @RequestParam("eventImgFile1") MultipartFile eventImgFile1,
                              @RequestParam("eventImgFile2") MultipartFile eventImgFile2) {
        if (bindingResult.hasErrors()) {
            return "event/eventForm";
        }
        try {
            eventService.saveEvent(eventFormDto, eventImgFile1, eventImgFile2);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "이벤트 등록 중 에러가 발생하였습니다");
            return "event/eventForm";
        }
        return "redirect:/event/ongoing";
    }

    @GetMapping(value = "/{eventId}") // 이벤트 상세페이지
    public String eventDtl(@PathVariable("eventId") Long eventId, Model model,
                           Principal principal) {
        EventFormDto eventFormDto = eventService.getEventDtl(eventId);
        model.addAttribute("eventFormDto", eventFormDto);

        // 유저 로그인 모달 함수
        populateAdminModel(model, principal);
        return "event/eventSub";
    }

    @GetMapping(value = "/{eventId}/edit") // 이벤트 수정폼
    public String eventEdit(@PathVariable("eventId") Long eventId,
                            Principal principal, Model model) {
        // 유저 로그인 모달 함수
        populateAdminModel(model, principal);
        EventFormDto eventFormDto = eventService.getEventDtl(eventId);
        model.addAttribute("eventFormDto", eventFormDto);
        return "event/eventForm";
    }

    @PostMapping(value = "/update") // 이벤트 수정
    public String eventUpdate(@Valid EventFormDto eventFormDto, BindingResult bindingResult,
                              @RequestParam("eventImgFile1") MultipartFile eventImgFile1,
                              @RequestParam("eventImgFile2") MultipartFile eventImgFile2,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return "event/eventForm";
        }
        try {
            eventService.updateEvent(eventFormDto, eventImgFile1, eventImgFile2);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "이벤트 등록 중 에러가 발생하였습니다");
            return "event/eventForm";
        }
        return "redirect:/event/ongoing";
    }

    @GetMapping("/{eventId}/delete") // 이벤트 삭제
    public String deleteEvent(@PathVariable("eventId") Long eventId) {
        // 이벤트 삭제 로직을 구현
        eventService.deleteEvent(eventId);

        // 삭제 후 리다이렉션할 URL을 반환
        return "redirect:/event/ongoing";
    }

    private void populateAdminModel(Model model, Principal principal) {
        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);

            // 리뷰수
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum",reviewNum);

            // 문의수
            List<Inquiry> inquiryList = inquiryRepository.findByWriterId(userInfo.getId());
            int inquiryNum = inquiryList.size();
            model.addAttribute("inquiryNum", inquiryNum);

            // 쿠폰수
            List<Member_CouponEntity> couponList = memberCouponRepository.findByMemberEmail(user);
            int couponNum = couponList.size();
            model.addAttribute("couponNum", couponNum);

            // ADMIN 권한 확인
            Role admin = userInfo.getRole();
            if (admin.equals(Role.ADMIN)) {
                model.addAttribute("admin", admin);
            }

            // 포인트 히스토리
            List<PointHistoryEntity> historys = pointHistoryRepository.findByMemberPointEntity_MemberEntityId(userInfo.getId());
            model.addAttribute("historys", historys);

            // 총 포인트 합산
            int totalPoints = 0;
            int totalPointsUsed = 0;

            for (PointHistoryEntity history : historys) {
                if (history.getActionType() == ActionType.EARNED) {
                    totalPoints += history.getPointsEarned();
                } else if (history.getActionType() == ActionType.USED) {
                    totalPointsUsed += history.getPointsUsed();
                }
            }
            int total = totalPoints - totalPointsUsed;
            model.addAttribute("total", total);
        }
    }


}
