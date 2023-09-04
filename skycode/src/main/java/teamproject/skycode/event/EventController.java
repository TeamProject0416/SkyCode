package teamproject.skycode.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import teamproject.skycode.review.Review;
import teamproject.skycode.review.ReviewForm;

@Controller
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;

    @GetMapping(value = "/ongoing")
    public String ongoingEvent(){
        return "/event/eventongoing";
    }
    @GetMapping(value = "/end")
    public String endEvent(){
        return "/event/eventend";
    }
    @GetMapping(value = "/winner")
    public String eventWinner(){
        return "/event/eventwinner";
    }
    @GetMapping(value = "/sub")
    public String eventSub(){ return "/event/eventSub";}

    @GetMapping(value = "/new")
    public String newEventForm(){
        return "event/eventForm";
    }

    @PostMapping(value = "/event/create")
    public String createReview(EventForm form){
        Event event = form.toEntity();
        Event saved = eventRepository.save(event);
        return "redirect:/event/eventongoing";
    }

}
