package teamproject.skycode.login;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

//    boolean existsByLonginId(String loginId);
//
//    Optional<User> findByLoginId(String loginId);
    Member findByEmail(String email);

}
