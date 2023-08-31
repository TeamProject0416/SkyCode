package teamproject.skycode.login;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findmember = memberRepository.findByEmail(member.getEmail());
        if (findmember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}



//    public boolean checkLoginIdDuplicate(String id){
//        return memberRepository.existsByLonginId(id);
//    }

/**
 * 회원가입 기능 1
 * 화면에서 JoinRequest(loginId, password, nickname)을 입력받아 User로 변환 후 저장
 * loginId, nickname 중복 체크는 Controller에서 진행 => 에러 메세지 출력을 위해
 */

/**
 *  로그인 기능
 *  화면에서 LoginRequest(loginId, password)을 입력받아 loginId와 password가 일치하면 User return
 *  loginId가 존재하지 않거나 password가 일치하지 않으면 null return
 */

//    public Member login(MemberRequest req) {
//        Optional<Member> optionalMember = memberRepository.findByLoginId(req.getLoginId());
//
//        // loginId와 일치하는 User가 없으면 null return
//        if(optionalMember.isEmpty()) {
//            return null;
//        }
//
//        Member member = optionalMember.get();
//
//        // 찾아온 User의 password와 입력된 password가 다르면 null return
//        if(!member.getPassword().equals(req.getPassword())) {
//            return null;
//        }
//
//        return member;
//    }

/**
 * loginId(String)를 입력받아 User을 return 해주는 기능
 * 인증, 인가 시 사용
 * loginId가 null이거나(로그인 X) userId로 찾아온 User가 없으면 null return
 * loginId로 찾아온 User가 존재하면 User return
 */
//    public Member getLoginUserByLoginId(String loginId) {
//        if(loginId == null) return null;
//
//        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
//        if(optionalMember.isEmpty()) return null;
//
//        return optionalMember.get();
//    }
//}

