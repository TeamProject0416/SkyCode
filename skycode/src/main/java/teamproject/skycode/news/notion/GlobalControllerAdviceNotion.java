package teamproject.skycode.news.notion;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
@ControllerAdvice("notion")
public class GlobalControllerAdviceNotion {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // This trims whitespace from all string parameters in form data
        binder.registerCustomEditor(String.class, new StringTrimmerEditor());
    }
}
