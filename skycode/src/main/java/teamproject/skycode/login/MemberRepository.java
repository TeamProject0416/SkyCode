package teamproject.skycode.login;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);

    Optional<Member> findByEmail(String email);

    List<Member> findAll();


//    비밀번호로 회원 조회 (보안을 위해 사용자 로그인 시 등)
    Optional<Member> findByEmailAndPassword(String email, String password);
}
