package teamproject.skycode.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping(value = {"/ongoing", "/ongoing/{page}"}) // 진행 페이지
    public String ongoingEvent(@PathVariable(name = "page", required = false) Integer page, Model model) {
        int pageSize = 3; // 페이지당 표시할 이벤트 수
        Pageable pageable = PageRequest.of(page != null ? page : 0, pageSize);

        // EventStatus.ONGOING 값을 사용하여 데이터 조회
        Page<EventEntity> eventPage = eventService.getEventPage(EventStatus.ONGOING, pageable);

        model.addAttribute("events", eventPage); // Page 객체를 그대로 넘김
        model.addAttribute("maxPage", 5); // 페이지당 표시할 최대 페이지 수

        return "/event/eventongoing";
    }

    @GetMapping(value = {"/end","/end/{page}"}) // 종료 페이지
    public String endEvent(@PathVariable(name = "page", required = false) Integer page,Model model) {
        int pageSize = 3; // 페이지당 표시할 이벤트 수
        Pageable pageable = PageRequest.of(page != null ? page : 0, pageSize);

        // EventStatus.ONGOING 값을 사용하여 데이터 조회
        Page<EventEntity> eventPage = eventService.getEventPage(EventStatus.END, pageable);

        model.addAttribute("events", eventPage); // Page 객체를 그대로 넘김
        model.addAttribute("maxPage", 5); // 페이지당 표시할 최대 페이지 수
        return "/event/eventend";
    }

    @GetMapping(value = {"/winner", "/winner/{page}"}) // 당첨자 페이지
    public String eventWinner(@PathVariable(name = "page", required = false) Integer page,Model model) {
        int pageSize = 3; // 페이지당 표시할 이벤트 수
        Pageable pageable = PageRequest.of(page != null ? page : 0, pageSize);

        // EventStatus.ONGOING 값을 사용하여 데이터 조회
        Page<EventEntity> eventPage = eventService.getEventPage(EventStatus.WINNER, pageable);

        model.addAttribute("events", eventPage); // Page 객체를 그대로 넘김
        model.addAttribute("maxPage", 5); // 페이지당 표시할 최대 페이지 수

        return "/event/eventwinner";
    }

    @GetMapping(value = "/new") // 이벤트 등록
    public String newEventForm(Model model) {
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
    public String eventDtl(@PathVariable("eventId") Long eventId, Model model) {
        EventFormDto eventFormDto = eventService.getEventDtl(eventId);
        model.addAttribute("eventFormDto", eventFormDto);
        return "event/eventSub";
    }

    @GetMapping(value = "/{eventId}/edit") // 이벤트 수정폼
    public String eventEdit(@PathVariable("eventId") Long eventId, Model model) {
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
            eventService.updateEvent(eventFormDto,eventImgFile1,eventImgFile2);
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

}
