package teamproject.skycode.review;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "review/reviewSub"; // 오류 페이지로 이동
    }

    // 다른 예외 처리 핸들러들을 여기에 추가할 수 있습니다.
}