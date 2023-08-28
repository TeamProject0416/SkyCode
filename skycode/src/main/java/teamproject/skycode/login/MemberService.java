package teamproject.skycode.login;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.skycode.myPage.users.EditDto;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository memberRepository;

  public Member saveMember(Member member){
      validateDuplicateMember(member);
      return memberRepository.save(member);
  }

  private void validateDuplicateMember(Member member){
      Member findmember = memberRepository.findByEmail(member.getEmail());
      if (findmember != null) {
          throw new IllegalStateException("이미 가입된 회원입니다.");
          }
      }




}






}

