package teamproject.skycode.login;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;



import javax.management.relation.Role;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "member")
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {


    @Id
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    private String confirmPassword;

    private String birthday;

    private String address;

    private String phone;


    public static Member createMember(MemberFormDto memberFormDto){
        Member member = new Member();
        member.setId(member.getId());
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        member.setBirthday(memberFormDto.getBirthday());
        member.setPhone(memberFormDto.getPhone());
        member.setPassword(memberFormDto.getPassword());
        member.setPassword(memberFormDto.getConfirmPassword());
        return member;
    }



}