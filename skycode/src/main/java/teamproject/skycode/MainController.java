package teamproject.skycode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import teamproject.skycode.event.EventEntity;
import teamproject.skycode.event.EventRepository;
import teamproject.skycode.login.MemberEntity;
import teamproject.skycode.login.MemberRepository;
import teamproject.skycode.review.ReviewEntity;
import teamproject.skycode.review.ReviewRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class MainController {
    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    @GetMapping(value = "/")
    public String skyCode(Model model, Principal principal) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum",reviewNum);
        }
        // 이벤트 캐러셀
        List<ReviewEntity> review = reviewRepository.findAllByOrderByReviewHitsDesc();
        model.addAttribute("reviews", review);

        // 이벤트 캐러셀
        List<EventEntity> event = eventRepository.findByONGOING();
        model.addAttribute("events", event);
        return "main";
    }

}
