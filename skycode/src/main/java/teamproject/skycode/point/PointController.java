package teamproject.skycode.point;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import teamproject.skycode.event.EventRepository;
import teamproject.skycode.login.MemberEntity;
import teamproject.skycode.login.MemberRepository;
import teamproject.skycode.review.ReviewEntity;
import teamproject.skycode.review.ReviewRepository;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin/point")
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final PointRepository pointRepository;

    @GetMapping(value = "/list") // 진행중인 포인트 목록 보이기
    public String pointList(Model model, Principal principal) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum", reviewNum);
        }

        List<PointEntity> points = pointRepository.findAllByOrderByRegTimeDesc();
        model.addAttribute("points", points);
        return "point/pointList";
    }

    @GetMapping(value = "/new") // 포인트 만들기
    public String pointNew(Model model, Principal principal) {

        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum", reviewNum);
        }

        model.addAttribute("pointFormDto", new PointFormDto());
        return "point/pointForm";
    }

    @PostMapping(value = "/new") // 포인트 등록
    public String newPoint(@Valid PointFormDto pointFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {

            return "point/pointForm";
        }
        try {
            pointService.savePoint(pointFormDto);
        } catch (Exception e) {

            model.addAttribute("errorMessage", "포인트 등록 중 에러가 발생하였습니다");
            return "point/pointForm";
        }
        return "redirect:/admin/point/list";
    }

    @GetMapping(value = "/{pointId}/edit") // 포인트 수정폼
    public String pointEdit(@PathVariable("pointId") Long pointId,
                             Principal principal, Model model) {

        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum", reviewNum);
        }

        PointFormDto pointFormDto = pointService.getPointDtl(pointId);
        model.addAttribute("pointFormDto", pointFormDto);
        return "point/pointForm";
    }

    @PostMapping(value = "/update") // 포인트 수정
    public String pointUpdate(@Valid PointFormDto pointFormDto, BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "point/pointForm";
        }
        try {
            pointService.updatePoint(pointFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "포인트 등록 중 에러가 발생하였습니다");
            return "point/pointForm";
        }
        return "redirect:/admin/point/list";
    }

    @GetMapping("/{pointId}/delete") // 포인트 삭제
    public String deletePoint(@PathVariable("pointId") Long pointId) {
        // 포인트 삭제 로직을 구현
        pointService.deletePoint(pointId);

        // 삭제 후 리다이렉션할 URL을 반환
        return "redirect:/admin/point/list";
    }

}
