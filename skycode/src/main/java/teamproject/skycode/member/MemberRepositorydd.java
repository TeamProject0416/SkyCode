package teamproject.skycode.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepositorydd extends JpaRepository<Member, Long> {

    Member findByEmail(String email);
}
