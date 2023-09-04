package teamproject.skycode.login;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@ToString
@Getter
@Setter
public class MemberDTO {

    private Long id;


    @NotBlank(message = "아이디를 입력해 주세요.")
    private String memberId;

    @NotBlank(message ="메일을 입력해 주세요.")
    private String memberEmail;

    @NotBlank(message ="비밀번호를 입력해 주세요.")
    @Length(min=8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String memberPassword;

    @NotBlank(message ="비밀번호를 다시 한 번 입력해 주세요.")
    private String memberPasswordCheck;

    @NotBlank(message ="이름을 입력해 주세요.")
    private String memberName;

    private String memberAddress;

    @NotBlank(message ="휴대전화를 입력해 주세요.")
    private String memberPhone;

    private String memberBirthday;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberId(memberEntity.getMemberId());
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
