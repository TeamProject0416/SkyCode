package teamproject.skycode.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "member_ej")
@Getter
@Setter
@ToString

public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String userId;

    @Column(unique = true)
    private String email;

    private String password;

    // 비밀번호 확인창
    private String passwordChck;

    private String phone;

    private String birthday;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setId(memberFormDto.getId());
        member.setName(memberFormDto.getName());
        member.setUserId(memberFormDto.getUserId());
        member.setEmail(memberFormDto.getEmail());
        member.setPhone(member.getPhone());
        member.setBirthday(member.getBirthday());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPasswordChck(memberFormDto.getPasswordChck());
        member.setPassword(password);
        member.setRole(Role.ADMIN);
        return member;
    }
}
