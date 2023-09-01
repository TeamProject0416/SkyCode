package teamproject.skycode.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;

    //회원 가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm() {
        return "login/save";
    }


    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        System.out.println("출력해줘");
        return "redirect:/";
    }

    @GetMapping("/member/login")
    public String loginForm(){
        return "login/login";
    }


    // 로그인 시 실패 또는 성공 했을 경우
   @PostMapping("/member/login")
   public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
       MemberDTO loginResult = memberService.login(memberDTO);
       if(loginResult != null){
           // login  성공
           // 이메일을 아이디로 바꿔야하나?
           session.setAttribute("loginEmail", loginResult.getMemberEmail());
        return "redirect:/";
       } else {
           // login 실패
           System.out.println("출력이 됐나?");
           return "login/login";

       }
    }



    // 회원 목록 출력하기
    @GetMapping("/member/")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        // 어떠한 html 로 가져갈 데이터가 있다면 model 사용
        model.addAttribute("memberList",memberDTOList);
        return "login/list";
        //리턴은 html 만들면 이름 바꾸기
    }


    @GetMapping("/member/{id}")
    public String findById(@PathVariable Long id, Model model){
      MemberDTO memberDTO = memberService.findById(id);
      model.addAttribute("member", memberDTO);
      return "detail";
      //리턴 html 만들면 이름 바꾸기
    }


//    수정하기
    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model){
       String myEmail = (String) session.getAttribute("loginEmail");
       MemberDTO memberDTO = memberService.updateForm(myEmail);
       model.addAttribute("updateMember", memberDTO);
       return "login/update";
    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        return "redirect:/member/" + memberDTO.getId();
    }



//    로그아웃
    @GetMapping("/member/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }


    // 이메일 중복 체크
    @PostMapping("/member/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail){
        System.out.println("memberEmail = " + memberEmail);
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;
//        if (checkResult != null) {
//            return "ok";
//        } else {
//            return "no";
//        }

    }

}
