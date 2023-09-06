package teamproject.skycode.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<MemberEntity, Long>{


    // 이메일로 회원 정보 조회    (select * from member_table where member_email=?)
   Optional<MemberEntity> findByMemberEmail(String memberEmail);
    Optional<MemberEntity> findByMemberId(String memberId);

    //이걸 아이디랑 비밀번호로 바꿔야 하나?


//    Optional<MemberEntity> findByMemberId(String memberId);

}