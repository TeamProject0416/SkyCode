package teamproject.skycode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import teamproject.skycode.event.EventEntity;
import teamproject.skycode.event.EventRepository;
import teamproject.skycode.event.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class MainController {
    private final EventRepository eventRepository;

    @GetMapping(value = "/")
    public String skyCode(Model model, Principal principal) {
        List<EventEntity> event = eventRepository.findByONGOING();
        model.addAttribute("events",event);
        String user = "";
        if (principal != null) {
            user = principal.getName();
        }
        System.out.println("user: " + user);
        model.addAttribute("user", user);
        return "main";
    }
}
