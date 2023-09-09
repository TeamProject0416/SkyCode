package teamproject.skycode.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        // 1. dot -> entity 변환
        // 2. repository 의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberEntity.setRole(Role.USER);
        memberRepository.save(memberEntity);
        //repository 의 save 메서드 호출 (조건. entity 객체를 넘겨줘야 함)
    }


    public MemberDTO login(MemberDTO memberDTO) {
        // 1.회원이 입력한 이메일로 DB에서 조회를 함
        // 2.DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단

        System.out.println(1234);
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberId(memberDTO.getMemberId());
        if (byMemberEmail.isPresent()) {
            System.out.println("아이디가 같나요?");
            // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
            MemberEntity memberEntity = byMemberEmail.get();
//            객체를 잘 가져와서 옵셔널을 잘 풀음

            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                // 비밀 번호 일치
                //entity -> dto 변환 후 리턴
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
//
                return dto;
            } else {
                System.out.println("비번 불일치");
                // 비밀 번호 불일치(로그인 실패
                return null;
            }
        } else {
            //조회 결과가 없다(해당 이메일을 가진 회원이 없다)
            return null;
        }
    }

    // 회원 출력
    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity : memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            return memberDTO;
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }

    }

//    public MemberDTO updateForm(String myEmail) {
//        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
//        if (optionalMemberEntity.isPresent()) {
//            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
//        } else {
//            return null;
//        }
//    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));

    }

//    회원 삭제

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }


    // 아이디 중복 체크
//    public String emailCheck(String memberEmail) {
//        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
//        if (byMemberEmail.isPresent()) {
//            // 조회 결과가 있다 -> 사용할 수 없다.
//            return null;
//        } else {
//            // 조회 결과가 없다 -> 사용할 수 있다.
//            return "ok";
//        }
//    }


    public void register(MemberDTO memberDTO) throws RegistrationException {
        String memberId = memberDTO.getMemberId();

        // 아이디 중복 검사
        if (isMemberIdAlreadyExists(memberId)) {
            throw new RegistrationException("이미 사용 중인 아이디입니다.");
        }

        // 나머지 회원 가입 로직 추가
    }


    // 아이디 중복 검사
    public boolean isMemberIdAlreadyExists(String memberId) {
        return memberRepository.existsByMemberId(memberId);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberEntity member = memberRepository.findByMemberEmail(email);

        if(member == null){
            throw new UsernameNotFoundException(email);
        }
        return User.builder()
                .username(member.getMemberEmail())
                .password(member.getMemberPassword())
                .roles(member.getRole().toString())
                .build();
    }

//    로그인 창에서 오류!!! ai가 알려줌
//    public boolean isValidMember(String memberId, String memberPassword) {
//        // 예시로 아이디와 비밀번호가 "testuser"와 "password"와 일치하는지 확인
//        return memberId.equals("testuser") && memberPassword.equals("password");
//    }
}













