package teamproject.skycode.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teamproject.skycode.constant.ActionType;
import teamproject.skycode.constant.Role;
import teamproject.skycode.event.EventEntity;
import teamproject.skycode.event.EventFormDto;
import teamproject.skycode.event.EventRepository;
import teamproject.skycode.login.MemberEntity;
import teamproject.skycode.login.MemberRepository;
import teamproject.skycode.news.inquiry.Inquiry;
import teamproject.skycode.news.inquiry.InquiryRepository;
import teamproject.skycode.point.PointHistoryEntity;
import teamproject.skycode.point.PointHistoryRepository;
import teamproject.skycode.review.ReviewEntity;
import teamproject.skycode.review.ReviewRepository;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final EventRepository eventRepository;
    private final InquiryRepository inquiryRepository;
    private final Member_CouponRepository memberCouponRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @GetMapping(value = "/admin/coupon/list") // 진행중인 쿠폰 목록 보이기
    public String couponList(Model model, Principal principal) {

        // 유저 로그인 모달 함수
        populateAdminModel(model, principal);
        List<CouponEntity> coupons = couponRepository.findByONGOING();
        model.addAttribute("coupons", coupons);
        return "coupon/couponList";
    }

    @GetMapping(value = "/admin/coupon/end") // 종료된 쿠폰 목록 보이기
    public String couponEnd(Model model, Principal principal) {

        // 유저 로그인 모달 함수
        populateAdminModel(model, principal);

        List<CouponEntity> coupons = couponRepository.findByEND();
        model.addAttribute("coupons", coupons);
        return "coupon/couponEnd";
    }

    @GetMapping(value = "/admin/coupon/new") // 쿠폰 만들기
    public String couponNew(Model model, Principal principal) {

        // 유저 로그인 모달 함수
        populateAdminModel(model, principal);

        model.addAttribute("couponFormDto", new CouponFormDto());
        return "coupon/couponForm";
    }

    @PostMapping(value = "/admin/coupon/new") // 쿠폰 등록
    public String newCoupon(@Valid CouponFormDto couponFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {

            return "coupon/couponForm";
        }
        try {
            couponService.saveCoupon(couponFormDto);
        } catch (Exception e) {

            model.addAttribute("errorMessage", "쿠폰 등록 중 에러가 발생하였습니다");
            return "coupon/couponForm";
        }
        return "redirect:/admin/coupon/list";
    }

    @GetMapping(value = "/admin/coupon/{couponId}/edit") // 쿠폰 수정폼
    public String couponEdit(@PathVariable("couponId") Long couponId,
                             Principal principal, Model model) {

        // 유저 로그인 모달 함수
        populateAdminModel(model, principal);

        CouponFormDto couponFormDto = couponService.getCouponDtl(couponId);
        model.addAttribute("couponFormDto", couponFormDto);
        return "coupon/couponForm";
    }

    @PostMapping(value = "/admin/coupon/update") // 쿠폰 수정
    public String couponUpdate(@Valid CouponFormDto couponFormDto, BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "coupon/couponForm";
        }
        try {
            couponService.updateCoupon(couponFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "쿠폰 등록 중 에러가 발생하였습니다");
            return "coupon/couponForm";
        }
        return "redirect:/admin/coupon/list";
    }

    @GetMapping("/admin/coupon/{couponId}/delete") // 쿠폰 삭제
    public String deleteCoupon(@PathVariable("couponId") Long couponId) {
        // 쿠폰 삭제 로직을 구현
        couponService.deleteCoupon(couponId);

        // 삭제 후 리다이렉션할 URL을 반환
        return "redirect:/admin/coupon/list";
    }


    @GetMapping("/coupon/{eventId}/couponDownload") // 쿠폰 다운로드
    public String couponDownload(@PathVariable("eventId") Long eventId, Principal principal) {

        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(EntityNotFoundException::new);
        String couponCode = event.getCouponCode(); //couponCode

        CouponEntity coupon = couponRepository.findByCode(couponCode);
        Optional<CouponEntity> couponEntityList = couponRepository.findById(coupon.getId());
        CouponEntity couponEntity = couponEntityList.get(); // couponEntity


        String user = principal.getName(); // MemberEmail
        MemberEntity userInfo = memberRepository.findByEmail(user);

        Optional<MemberEntity> memberEntityList = memberRepository.findById(userInfo.getId());
        MemberEntity memberEntity = memberEntityList.get(); // memberEntity

        // Member_CouponEntity 생성 및 저장
        Member_CouponEntity memberCouponEntity = new Member_CouponEntity();
        memberCouponEntity.setCouponCode(couponCode);
        memberCouponEntity.setMemberEmail(user);
        memberCouponEntity.setMemberEntity(memberEntity);
        memberCouponEntity.setCouponEntity(couponEntity);



        memberCouponRepository.save(memberCouponEntity);

        return "redirect:/event/" + eventId;

    }

    private void populateAdminModel(Model model, Principal principal) {
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
    }


}
