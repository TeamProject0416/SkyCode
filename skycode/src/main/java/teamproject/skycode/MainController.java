package teamproject.skycode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import teamproject.skycode.event.EventEntity;
import teamproject.skycode.event.EventRepository;
import teamproject.skycode.event.EventService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final EventRepository eventRepository;
    @GetMapping(value = "/")
    public String skyCode(Model model) {
        List<EventEntity> event = eventRepository.findByONGOING();
        model.addAttribute("events",event);
        return "main";
    }
}
