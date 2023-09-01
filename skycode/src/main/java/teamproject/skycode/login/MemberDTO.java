package teamproject.skycode.login;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private Long id;

    private String memberEmail;

    private String memberPassword;

    private String memberPasswordCheck;

    private String memberName;

    private String memberAddress;

    private String memberPhone;

    private String memberBirthday;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberPasswordCheck(memberDTO.getMemberPasswordCheck());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberAddress(memberEntity.getMemberAddress());
        memberDTO.setMemberPhone(memberDTO.getMemberPhone());
        memberDTO.setMemberBirthday(memberDTO.getMemberBirthday());
        return memberDTO;
    }


}
