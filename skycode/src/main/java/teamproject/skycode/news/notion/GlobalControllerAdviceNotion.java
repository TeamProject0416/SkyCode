package teamproject.skycode.news.notion;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
@ControllerAdvice("notion")
public class GlobalControllerAdviceNotion {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // This trims whitespace from all string parameters in form data
        binder.registerCustomEditor(String.class, new StringTrimmerEditor());
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
        return "errorPage"; // errorPage는 실제로 존재하는 뷰 이름이어야 합니다.
    }
}
