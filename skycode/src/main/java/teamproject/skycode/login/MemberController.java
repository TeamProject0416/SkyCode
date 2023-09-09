package teamproject.skycode.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {

    // 생성자 주입
    private final MemberService memberService;
    private final MemberRepository memberRepository;


    //회원 가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm(Model model) {
        MemberDTO memberDTO = new MemberDTO();
        model.addAttribute("memberDTO", memberDTO);
        return "member/save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute @Valid MemberDTO memberDTO, BindingResult bindingResult, Model model) {
        model.addAttribute("memberDTO", memberDTO);
        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류가 있는 경우 오류 메시지를 리다이렉트 시키기 위해 RedirectAttributes를 사용
//            model.addAttribute("errorMessage", "입력한 데이터에 오류가 있습니다.");
            System.out.println("오류 발생!!!!!!");
            return "member/save"; // 오류가 발생하면 다시 회원가입 폼 페이지로 이동
        } else {
            memberService.save(memberDTO);
            // 유효성 검사가 통과한 경우 데이터를 저장하고 메시지를 전달
            System.out.println("회원가입 성공");
//            model.addAttribute("successMessage", "데이터가 성공적으로 저장되었습니다.");
            return "/member/login"; // 성공 시 다른 페이지로 리다이렉트
        }
    }

//    비밀번호 일치 하지 않을 경우!!
    @PostMapping(value = "/member/passerror")
    public String memberForm(@Valid MemberDTO memberDTO,
                             BindingResult bindingResult) {
        // 로그인 시 비밀번호 일치하는지
        if (bindingResult.hasErrors()) {
            System.out.println("로그인 일치");
            return "member/save";
        }
        if (memberDTO.getMemberPassword().equals(memberDTO.getMemberPasswordCheck())) {
            bindingResult.rejectValue("confirmPassword",
                    "password.mismatch", "비밀번호가 일치하지 않습니다.");
            System.out.println("로그인 불일치");
        }
        return "member/save";
    }


//  비밀번호 일치하는지 -> 이게 지금 작동 안되서 스크립트 사용 했는데 그래도 되나?
//    @PostMapping(value = "/member/new")
//    public String memberForm(@Valid MemberDTO memberDTO,
//                             BindingResult bindingResult, Model model,
//                             Authentication authentication) {
//        // 로그인 시 비밀번호 일치하는지
//        if (bindingResult.hasErrors()) {
//            return "member/save";
//        }
//        if (!memberDTO.getMemberPassword().equals(memberDTO.getMemberPasswordCheck())) {
//            bindingResult.rejectValue("memberPasswordCheck",
//                    "password.mismatch", "비밀번호가 일치하지 않습니다.");
//            return "member/save";
//        }
//        try {
//            memberService.register(memberDTO);
//            // 회원 가입이 성공하면 로그인 페이지로 리다이렉트 또는 다른 페이지로 이동
//            return "member/login"; // 로그인 페이지로 리다이렉트 예시
//        } catch (Exception e) {
//            // 회원 가입 중에 오류가 발생한 경우 에러 처리
//            // 예를 들어, 중복된 이메일 주소 등에 대한 처리를 추가할 수 있습니다.
//            model.addAttribute("registrationError", "회원 가입 중 오류가 발생했습니다.");
//            return "member/save";
//        }
//    }


    //    로그인페이지
    @GetMapping("/member/login")
    public String loginForm() {
        return "member/login";
    }


    // 로그인 시 실패 또는 성공 했을 경우 오류창
    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, Model model, Principal principal) {

        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // login  성공
//            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            System.out.println("_____________________________________");

            model.addAttribute("loginEmail", loginResult.getMemberId());

            return "redirect:/";
        } else {
            // login 실패
            model.addAttribute("loginErrorMsg", "아이디와 비밀번호를 확인해 주세요.");
            System.out.println("로그인실패");
            return "member/login";

        }
    }


    // 회원 가입 시 오류
//    @GetMapping("/member/signup")
//    public String showSignUpForm(Model model){
//        model.addAttribute("memberDTO", new MemberDTO());
//        return "member/save";
//    }


    // 회원 목록 출력하기
//    @GetMapping("/member/list")
//    public String findAll(Model model) {
//        List<MemberDTO> memberDTOList = memberService.findAll();
//        // 어떠한 html 로 가져갈 데이터가 있다면 model 사용
//        model.addAttribute("memberList", memberDTOList);
//        return "member/list";
//        //리턴은 html 만들면 이름 바꾸기
//    }
//
//
//    @GetMapping("/member/{id}")
//    public String findById(@PathVariable Long id, Model model) {
//        MemberDTO memberDTO = memberService.findById(id);
//        model.addAttribute("member", memberDTO);
//        return "member/detail";
//        //리턴 html 만들면 이름 바꾸기
//    }


    //  수정하기
//    @GetMapping("/member/update")
//    public String updateForm(HttpSession session, Model model) {
//        String myEmail = (String) session.getAttribute("loginEmail");
//        MemberDTO memberDTO = memberService.updateForm(myEmail);
//        model.addAttribute("updateMember", memberDTO);
//        return "member/update";
//    }
//
//    @PostMapping("/member/update")
//    public String update(@ModelAttribute MemberDTO memberDTO) {
//        memberService.update(memberDTO);
//        return "redirect:/member/" + memberDTO.getId();
//    }


    //    로그아웃
    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    // 아이디 중복 체크
//    @PostMapping("/member/email-check")
//    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
//        System.out.println("memberEmail = " + memberEmail);
//        String checkResult = memberService.emailCheck(memberEmail);
//        return checkResult;
////        if (checkResult != null) {
////            return "ok";
////        } else {
////            return "no";
////        }
////    }
//    }

    // 로그인 창에서 오류

//    @GetMapping(value = "/member/error")
//    public String loginError(Model model) {
//        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
//        return "member/login";
//    }




}










