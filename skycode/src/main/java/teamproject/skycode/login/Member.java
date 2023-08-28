package teamproject.skycode.login;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.management.relation.Role;
import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "member")
@ToString
public class Member extends BaseEntity{

    @Id
    @Column(name = "Member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String password;

    private String name;

    private String birthday;

    private String address;

    private String phone;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;



    public static Member createMember(MemberFormDto memberFormDto){
        Member member = new Member();
        member.setId(member.getId());
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        member.setBirthday(memberFormDto.getBirthday());
//        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPhone(memberFormDto.getPhone());
        member.setRole(Role.ADMIN);
    }


}
