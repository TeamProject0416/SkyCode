package teamproject.skycode.myPage;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import teamproject.skycode.constant.ActionType;
import teamproject.skycode.coupon.CouponEntity;
import teamproject.skycode.coupon.CouponRepository;
import teamproject.skycode.coupon.Member_CouponEntity;
import teamproject.skycode.coupon.Member_CouponRepository;
import teamproject.skycode.login.MemberEntity;
import teamproject.skycode.login.MemberRepository;
import teamproject.skycode.login.MemberService;
import teamproject.skycode.myPage.user_shopping.CouponDto;
import teamproject.skycode.myPage.users.MemberEditFormDto;
import teamproject.skycode.myPage.users.PasswordFormDto;
import teamproject.skycode.news.inquiry.Inquiry;
import teamproject.skycode.news.inquiry.InquiryRepository;
import teamproject.skycode.point.PointEntity;
import teamproject.skycode.point.PointHistoryEntity;
import teamproject.skycode.point.PointHistoryRepository;
import teamproject.skycode.review.ReviewEntity;
import teamproject.skycode.review.ReviewRepository;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final CouponRepository couponRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final Member_CouponRepository memberCouponRepository;

    private final InquiryRepository inquiryRepository;


    //---------------------------/user------------------------//
    @GetMapping(value = "user") // 유저 모두 보기
    public String user(Model model, Principal principal) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum", reviewNum);
        }

        return "myPage/users/user";
    }


    //---------------------------/user/edit------------------------//
    @GetMapping(value = "/user/edit") // 회원 정보 폼
    public String userEdit(Model model, Principal principal) {


        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            System.err.println("sdasdfsdf");

            MemberEditFormDto memberEditFormDto = memberService.getMemberDtl(userInfo.getId());
            model.addAttribute("memberEditFormDto", memberEditFormDto);

            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum", reviewNum);
        }
        return "myPage/users/edit";
    }

    @PostMapping(value = "/user/update") // 회원 정보 수정
    public String userUpdate(@Valid MemberEditFormDto memberEditFormDto, BindingResult bindingResult,
                             @RequestParam("userImgFile") MultipartFile userImgFile,
                             Principal principal, Model model) {

        if (bindingResult.hasErrors()) {
            return "myPage/users/edit";
        }
        try {
            if (principal.getName().equals(memberEditFormDto.getEmail())) {
                memberService.updateUser(memberEditFormDto, userImgFile);
                model.addAttribute("successMessage", "회원 정보 수정이 완료되었습니다");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "회원 정보 수정 중 에러가 발생하였습니다");
            return "myPage/users/edit";
        }
        return "myPage/users/edit";
    }

    @GetMapping("/user/delete/{memberId}") // 회원 삭제
    public String userDelete(@PathVariable("memberId") Long memberId,
                             Authentication authentication, HttpServletRequest request,
                             HttpServletResponse response, Principal principal, Model model) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum", reviewNum);
        }

        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(EntityNotFoundException::new);
        model.addAttribute("passwordFormDto", new PasswordFormDto());
        try {
            if (principal.getName().equals(member.getEmail())) {
                memberRepository.delete(member);

                // 회원 탈퇴 후 로그아웃
                new SecurityContextLogoutHandler().logout(request, response, authentication);

                return "redirect:/member/login?message=" + URLEncoder.encode("탈퇴가 완료되었습니다", "UTF-8");
            }
        } catch (Exception e) {
            model.addAttribute("errorDelete", "탈퇴 중 에러가 발생하였습니다");
            return "/myPage/users/edit";
        }
        return "redirect:/";
    }


    //---------------------------/user/edit_password------------------------//
    @GetMapping(value = "/user/edit_password")
    public String userEditPw(Model model, Principal principal) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum", reviewNum);
        }

        model.addAttribute("passwordFormDto", new PasswordFormDto());
        return "myPage/users/edit_password";
    }

    @PostMapping("/user/edit_password/update")
    public String userEditPwUpdate(PasswordFormDto passwordFormDto, Principal principal,
                                   Authentication authentication, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {
        // 현재 로그인한 사용자의 이메일 가져오기
        String userEmail = principal.getName();

        // 사용자 정보 가져오기
        MemberEntity user = memberRepository.findByEmail(userEmail);

        try {
            if (user != null && passwordEncoder.matches(passwordFormDto.getCheckPassword(), user.getPassword())) {
                // 현재 비밀번호가 일치하면 새로운 비밀번호 설정
                String newPassword = passwordEncoder.encode(passwordFormDto.getNewPassword());
                user.setPassword(newPassword);
                memberRepository.save(user);

                // 비밀번호 변경 후 로그아웃
                new SecurityContextLogoutHandler().logout(request, response, authentication);

                return "redirect:/member/login?message=" + URLEncoder.encode("비밀번호가 변경되었습니다.", "UTF-8");
            }
        } catch (Exception e) {
            // 현재 비밀번호가 일치하지 않을 경우 오류 메시지 표시
            model.addAttribute("errorUpdate", "비밀번호 변경 중 에러가 발생하였습니다");
            return "/myPage/users/edit_password";
        }
        return "redirect:/";
    }


    //---------------------------/user_shopping/orderList------------------------//
    @GetMapping(value = "/user_shopping/orderList")
    public String userOrderList(Model model, Principal principal) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum", reviewNum);


        }

        return "myPage/shopping/orderList";
    }


    //---------------------------/user_shopping/point------------------------//
    @GetMapping(value = "/user_shopping/point")
    public String userPoint(Model model, Principal principal) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum", reviewNum);

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

        return "myPage/shopping/point";
    }


    //---------------------------/user_shopping/coupon------------------------//
    @GetMapping(value = "/user_shopping/coupon")
    public String userCoupon(Model model, Principal principal) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum", reviewNum);

            // 쿠폰 목록
            List<Member_CouponEntity> MemberCouponList = memberCouponRepository.findByMemberEmail(user);
            model.addAttribute("memberCouponList", MemberCouponList);
        }

        model.addAttribute("couponDto", new CouponDto());

        return "myPage/shopping/coupon";
    }

    @PostMapping("/user/couponDownload") // 쿠폰 다운로드
    public String couponDownload(@Valid CouponDto couponDto, BindingResult bindingResult, Model model, Principal principal) {

        if (bindingResult.hasErrors()) {
            return "/user_shopping/coupon";
        }
        try {
            String couponCode = couponDto.getCouponCode(); //couponCode
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
        } catch (Exception e) {
            model.addAttribute("errorMessage", "이벤트 등록 중 에러가 발생하였습니다");
            return "/user_shopping/coupon";
        }
        return "redirect:/user_shopping/coupon";
    }


    //---------------------------/user_shopping/questions------------------------//
    @GetMapping(value = "/user_shopping/questions")
    public String userQuestions(Model model, Principal principal) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);

            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum", reviewNum);

            List<Inquiry> inquiryList = inquiryRepository.findByWriterId(userInfo.getId());
            model.addAttribute("inquiryList", inquiryList);
        }
        return "myPage/shopping/questions";
    }


    //---------------------------/user_review------------------------//
    @GetMapping(value = "/user_review")
    public String userReview(Model model, Principal principal) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);

            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum", reviewNum);

            // 각 ReviewEntity에 대한 CommentEntity의 총 수를 가져오기
            for (ReviewEntity reviewEntity : review) {
                ReviewEntity reviewWithCommentCount = reviewRepository.findWithCommentCount(reviewEntity.getId());
                reviewEntity.setCommentCount(reviewWithCommentCount.getCommentEntityList().size());
            }

            model.addAttribute("reviews", review);
        }
        return "myPage/review/review";
    }

}
