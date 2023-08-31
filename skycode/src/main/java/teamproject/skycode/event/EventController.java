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
//    private final EventFormDto eventFormDto;

    @GetMapping(value = "/ongoing")
    public String ongoingEvent(Model model) {
        List<EventEntity> events = eventRepository.findAll();
        model.addAttribute("events", events);
        return "/event/eventongoing";
    }

    @GetMapping(value = "/end")
    public String endEvent() {
        return "/event/eventend";
    }

    @GetMapping(value = "/winner")
    public String eventWinner() {
        return "/event/eventwinner";
    }

    @GetMapping(value = "/sub")
    public String eventSub() {
        return "/event/eventSub";
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
        System.out.println("err");
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

}
