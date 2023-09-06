package teamproject.skycode.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventRepository eventRepository;

    @GetMapping(value = "/ongoing")
    public String ongoingEvent(Model model) {
        List<EventEntity> events = eventRepository.findByONGOING();
        model.addAttribute("events", events);
        return "/event/eventongoing";
    }

    @GetMapping(value = "/end")
    public String endEvent(Model model) {
        List<EventEntity> events = eventRepository.findByEND();
        model.addAttribute("events", events);
        return "/event/eventend";
    }

    @GetMapping(value = "/winner")
    public String eventWinner(Model model) {
        List<EventEntity> events = eventRepository.findByWINNER();
        model.addAttribute("events", events);
        return "/event/eventwinner";
    }

    @GetMapping(value = "/new")
    public String newEventForm(Model model) {
        model.addAttribute("eventFormDto", new EventFormDto());
        return "event/eventForm";
    }

    @PostMapping(value = "/new")
    public String createReview(@Valid EventFormDto eventFormDto, BindingResult bindingResult, Model model,
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

    @GetMapping(value = "/{eventId}")
    public String eventDtl(@PathVariable("eventId") Long eventId, Model model) {
        EventFormDto eventFormDto = eventService.getEventDtl(eventId);
        model.addAttribute("eventFormDto", eventFormDto);
        return "event/eventSub";
    }

    @GetMapping(value = "/{eventId}/edit")
    public String eventEdit(@PathVariable("eventId") Long eventId, Model model) {
        EventFormDto eventFormDto = eventService.getEventDtl(eventId);
        model.addAttribute("eventFormDto", eventFormDto);
        return "event/eventForm";
    }

    @PostMapping(value = "/update")
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

    @GetMapping("/{eventId}/delete")
    public String deleteEvent(@PathVariable("eventId") Long eventId) {
        // 이벤트 삭제 로직을 구현
        eventService.deleteEvent(eventId);

        // 삭제 후 리다이렉션할 URL을 반환
        return "redirect:/event/ongoing";
    }

}
