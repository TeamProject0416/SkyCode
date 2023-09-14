package teamproject.skycode.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<MemberEntity, Long>{

   MemberEntity findByEmail(String email);

   MemberEntity findByNickName(String nickName);
}