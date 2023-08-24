package teamproject.skycode.event;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/event")
public class eventController {
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
}
