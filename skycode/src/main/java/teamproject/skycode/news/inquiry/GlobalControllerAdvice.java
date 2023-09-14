package teamproject.skycode.news.inquiry;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import teamproject.skycode.news.notion.StringTrimmerEditor;

@ControllerAdvice("inquiry")
public class GlobalControllerAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // This trims whitespace from all string parameters in form data
        binder.registerCustomEditor(String.class, new StringTrimmerEditor());
    }
}
