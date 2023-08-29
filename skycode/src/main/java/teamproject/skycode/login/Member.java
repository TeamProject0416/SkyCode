package teamproject.skycode.login;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;



import javax.management.relation.Role;

import javax.persistence.*;


@Getter
@Setter
@Entity

@Table(name = "member")
@ToString
public class Member {


    @Id
    // 밑 두개는 더미데이터때문에 에러날까봐 넣은거임
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    private String birthday;

    private String address;

    private String phone;

//    @Enumerated(EnumType.STRING)
//    private Role role;



    public static Member createMember(MemberFormDto memberFormDto){
        Member member = new Member();
        member.setId(member.getId());
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        member.setBirthday(memberFormDto.getBirthday());
        member.setPhone(memberFormDto.getPhone());
        return member;
    }



}
