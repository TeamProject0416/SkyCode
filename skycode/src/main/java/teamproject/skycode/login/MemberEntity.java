package teamproject.skycode.login;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name ="member_table")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(unique = true)  // unique 제약 조건 추가
    private String memberId;

    @Column(unique = true)  // unique 제약 조건 추가
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberPasswordCheck;

    @Column
    private String memberName;

    @Column
    private String memberAddress;

    @Column
    private String memberPhone;

    @Column
    private String memberBirthday;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberId(memberDTO.getMemberId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberPasswordCheck(memberDTO.getMemberPasswordCheck());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberAddress(memberDTO.getMemberAddress());
        memberEntity.setMemberPhone(memberDTO.getMemberPhone());
        memberEntity.setMemberBirthday(memberDTO.getMemberBirthday());
        return memberEntity;
    }

//    수정
    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberPasswordCheck(memberDTO.getMemberPasswordCheck());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberAddress(memberDTO.getMemberAddress());
        memberEntity.setMemberPhone(memberDTO.getMemberPhone());
        memberEntity.setMemberBirthday(memberDTO.getMemberBirthday());
        return memberEntity;
    }
}

