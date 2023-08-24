package teamproject.skycode.mainController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
//@RequestMapping(value = "/main")
public class MainController {

        @GetMapping(value = "/")
        public String skyCode() {
            return "main";
        }

        @GetMapping(value = "/map")
        public String map() {
            return "map/map";
        }
}
