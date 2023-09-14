package teamproject.skycode.myPage.users;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import teamproject.skycode.constant.Gender;
import teamproject.skycode.login.MemberEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Getter
@Setter
public class PasswordFormDto {

//    private String email;

    private String checkPassword;

    @Length(min = 8,max = 16,message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String newPassword;

    private static ModelMapper modelMapper = new ModelMapper();

    public static PasswordFormDto of(MemberEntity member) {
        return modelMapper.map(member, PasswordFormDto.class);
    }
}
