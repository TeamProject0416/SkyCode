package teamproject.skycode.login;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    // 생성자 주입
    private final MemberService memberService;
    private final MemberRepository memberRepository;


    //회원 가입 페이지 출력 요청
    @GetMapping("/member/save")

    public String saveForm() {

        return "login/save";


//     public String saveForm(Model model) {
//         MemberDTO memberDTO = new MemberDTO();
//         model.addAttribute("memberDTO", memberDTO);
//         return "member/save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute @Valid MemberDTO memberDTO, BindingResult bindingResult, Model model) {
        memberService.save(memberDTO);

        System.out.println("출력해줘");

        return "login/login";

//        return "redirect:/";

    }
//        return "redirect:/";

//         model.addAttribute("memberDTO", memberDTO);
//         if (bindingResult.hasErrors()) {
//             // 유효성 검사 오류가 있는 경우 오류 메시지를 리다이렉트 시키기 위해 RedirectAttributes를 사용
//             model.addAttribute("errorMessage", "입력한 데이터에 오류가 있습니다.");
//             System.out.println("오류가 떠서 메인 페이지로 안넘어가");
//             return "member/save"; // 오류가 발생하면 다시 회원가입 폼 페이지로 이동
//         } else {

//             // 유효성 검사가 통과한 경우 데이터를 저장하고 메시지를 전달
// //            memberService.save(memberDTO);
//             System.out.println("회원가입 성공");
//             model.addAttribute("successMessage", "데이터가 성공적으로 저장되었습니다.");
//             return "/member/login"; // 성공 시 다른 페이지로 리다이렉트
//         }

//     }

//    @PostMapping("/member/save")    // name 값을 requestparam 에 담아온다
//    public String save(@ModelAttribute @Valid MemberDTO memberDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
////        if (bindingResult.hasErrors()) {
////            redirectAttributes.addFlashAttribute("errorMessage", "입력한 데이터에 오류가 있습니다.");
////             // 유효성 검사 오류가 있는 경우 다시 회원가입 폼을 보여줌
////            return "member/save";
////        }
////        System.out.println("출력해줘");
//////        model.addAttribute("successMessage", "회원가입이 완료되었습니다.");
////        memberService.save(memberDTO);
////        redirectAttributes.addFlashAttribute("successMessage", "데이터가 성공적으로 저장되었습니다.");
//
//        MemberEntity memberEntity = memberDTO.toEntity();
//        memberRepository.save(memberEntity);
//        return "member/login";
////        return "redirect:/";
//    }

//    @PostMapping("/your-post-endpoint")
//    public String handleFormSubmission(@ModelAttribute @Valid YourDTO yourDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            // 유효성 검사 오류가 있는 경우 오류 메시지를 리다이렉트 시키기 위해 RedirectAttributes를 사용
//            redirectAttributes.addFlashAttribute("errorMessage", "입력한 데이터에 오류가 있습니다.");
//            return "redirect:/your-form-page"; // 오류가 발생하면 다시 폼 페이지로 리다이렉트
//        }
//
//        // 유효성 검사가 통과한 경우 데이터를 저장하고 메시지를 전달
//        yourService.save(yourDTO);
//        redirectAttributes.addFlashAttribute("successMessage", "데이터가 성공적으로 저장되었습니다.");
//        return "redirect:/"; // 성공 시 다른 페이지로 리다이렉트
//    }

//    @PostMapping("/save")
//    public String saveMember(@Valid MemberDTO memberDTO, BindingResult bindingResult, Model model) {
//
//        // 유효성 검사를 통과한 경우, 회원 정보를 저장하는 로직을 구현
//        // 이 코드에서는 단순히 예시로 표시하고 저장 로직을 추가해야 합니다.
//        if (bindingResult.hasErrors()) {
//            // 유효성 검사 오류가 있는 경우 다시 회원가입 폼을 보여줌
//            return "member/save";
//        }
//        // 회원가입 성공 메시지를 모델에 추가
//        model.addAttribute("successMessage", "회원가입이 완료되었습니다.");
//
//        // 회원가입 성공 페이지로 리다이렉트
//        return "redirect:";
//    }



//    로그인페이지
    @GetMapping("/member/login")
    public String loginForm() {
        return "member/login";
    }

    // 로그인 시 실패 또는 성공 했을 경우 //session: 로그인 유지
    @PostMapping("/member/logins")
    public String login(@ModelAttribute MemberDTO memberDTO, Model model) {

        System.out.println("d" + memberDTO.getMemberId());
        System.out.println("d" + memberDTO.getMemberPassword());
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // login  성공
//            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            model.addAttribute("loginEmail", loginResult.getMemberId());
            System.out.println("로그인성공");
//            return "login/main";
            return "redirect:/";
        } else {
            // login 실패
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
    @GetMapping("/member/list")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        // 어떠한 html 로 가져갈 데이터가 있다면 model 사용
        model.addAttribute("memberList", memberDTOList);
        return "member/list";
        //리턴은 html 만들면 이름 바꾸기
    }


    @GetMapping("/member/{id}")
    public String findById(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "member/detail";
        //리턴 html 만들면 이름 바꾸기
    }


    //  수정하기
    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model) {
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "member/update";
    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "redirect:/member/" + memberDTO.getId();
    }


    //    로그아웃
    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    // 아이디 중복 체크
    @PostMapping("/member/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        System.out.println("memberEmail = " + memberEmail);
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;
//        if (checkResult != null) {
//            return "ok";
//        } else {
//            return "no";
//        }
//    }
    }

    // 로그인 창에서 오류

    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "member/login";
    }



}






