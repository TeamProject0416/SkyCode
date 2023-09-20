package teamproject.skycode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import teamproject.skycode.constant.ActionType;
import teamproject.skycode.constant.Role;
import teamproject.skycode.coupon.Member_CouponEntity;
import teamproject.skycode.coupon.Member_CouponRepository;
import teamproject.skycode.event.EventEntity;
import teamproject.skycode.event.EventRepository;
import teamproject.skycode.login.MemberEntity;
import teamproject.skycode.login.MemberRepository;
import teamproject.skycode.news.inquiry.Inquiry;
import teamproject.skycode.news.inquiry.InquiryRepository;
import teamproject.skycode.point.PointHistoryEntity;
import teamproject.skycode.point.PointHistoryRepository;
import teamproject.skycode.review.ReviewEntity;
import teamproject.skycode.review.ReviewRepository;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final InquiryRepository inquiryRepository;
    private final Member_CouponRepository memberCouponRepository;
    private final PointHistoryRepository pointHistoryRepository;


    @GetMapping(value = "/")
    public String skyCode(Model model, Principal principal) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);

            // 리뷰수
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum",reviewNum);

            // 문의수
            List<Inquiry> inquiryList = inquiryRepository.findByWriterId(userInfo.getId());
            int inquiryNum = inquiryList.size();
            model.addAttribute("inquiryNum", inquiryNum);

            // 쿠폰수
            List<Member_CouponEntity> couponList = memberCouponRepository.findByMemberEmail(user);
            int couponNum = couponList.size();
            model.addAttribute("couponNum", couponNum);

            // ADMIN 권한 확인
            Role admin = userInfo.getRole();
            if (admin.equals(Role.ADMIN)) {
                model.addAttribute("admin", admin);
            }

            // 포인트 히스토리
            List<PointHistoryEntity> historys = pointHistoryRepository.findByMemberPointEntity_MemberEntityId(userInfo.getId());
            model.addAttribute("historys", historys);

            // 총 포인트 합산
            int totalPoints = 0;
            int totalPointsUsed = 0;

            for (PointHistoryEntity history : historys) {
                if (history.getActionType() == ActionType.EARNED) {
                    totalPoints += history.getPointsEarned();
                } else if (history.getActionType() == ActionType.USED) {
                    totalPointsUsed += history.getPointsUsed();
                }
            }
            int total = totalPoints - totalPointsUsed;
            model.addAttribute("total", total);
        }

        // 리뷰 캐러셀
        List<ReviewEntity> review = reviewRepository.findAllByOrderByReviewHitsDesc();
        model.addAttribute("reviews", review);

        // 이벤트 캐러셀
        List<EventEntity> event = eventRepository.findByONGOING();
        model.addAttribute("events", event);
        return "main";
    }

}
