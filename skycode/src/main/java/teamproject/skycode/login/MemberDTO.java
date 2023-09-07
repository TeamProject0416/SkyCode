package teamproject.skycode.login;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@ToString
@Getter
@Setter
public class MemberDTO {

    private Long id;


    @NotBlank(message = "아이디를 입력해 주세요.")
    private String memberId;


    @Pattern(regexp = "^[A-Za-z0-9_.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+", message = "이메일을 입력해 주세요.")
    private String memberEmail;

//    @NotBlank(message ="비밀번호를 입력해 주세요.")
    @Length(min=4, max=8, message = "비밀번호는 4자 이상, 8자 이하로 입력해 주세요.")
    private String memberPassword;

    @NotBlank(message ="비밀번호를 다시 한 번 입력해 주세요.")
    private String memberPasswordCheck;

    @NotBlank(message ="이름을 입력해 주세요.")
    private String memberName;

    @NotBlank(message = "주소를 입력해 주세요.")
    private String memberAddress;

    @NotNull(message ="휴대전화를 입력해 주세요.")
    private String memberPhone;

    @NotBlank(message ="생일을 입력해 주세요.")
    private String memberBirthday;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberId(memberEntity.getMemberId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberPasswordCheck(memberEntity.getMemberPasswordCheck());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberAddress(memberEntity.getMemberAddress());
        memberDTO.setMemberPhone(memberEntity.getMemberPhone());
        memberDTO.setMemberBirthday(memberEntity.getMemberBirthday());
        return memberDTO;
    }


//    public MemberEntity toEntity() {
//        MemberEntity memberEntity = new MemberEntity();
//        memberEntity.setId(this.id);
//        memberEntity.setMemberName(this.memberName);
//        memberEntity.setMemberPassword(this.memberPassword);
//        memberEntity.setMemberId(this.memberId);
//        memberEntity.setMemberPhone(this.memberPhone);
//        memberEntity.setMemberBirthday(this.memberBirthday);
//        memberEntity.setMemberAddress(this.memberAddress);
//        memberEntity.setMemberEmail(this.memberEmail);
//        return memberEntity;
//    }
}
