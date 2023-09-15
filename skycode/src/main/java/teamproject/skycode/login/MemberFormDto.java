package teamproject.skycode.login;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Getter
@Setter
public class MemberFormDto {

    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;

    @NotBlank(message = "닉네임을 입력해 주세요.")
    private String nickName;


    //    @Pattern(regexp = "^[A-Za-z0-9_.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+", message = "이메일을 입력해 주세요.")
    @NotEmpty(message = "이메일은 필수 입력 값입니다")
    @Email(message = "이메일 형식으로 입력해주세요")
    @Pattern(regexp = "^((\\w+\\-?)+@(\\w+\\.)*\\w+)|(\"[\\w\\.]+\"\\s*<\\s*(\\w+\\-?)+@(\\w+\\.)*\\w+\\s*>)$", message = "skycode@skycode.com 형식으로 작성해주세요")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다")
    @Length(min = 8,max = 16,message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String password;

//    @NotBlank(message ="비밀번호를 다시 한 번 입력해 주세요.")
//    private String memberPasswordCheck;

    @NotBlank(message = "주소를 입력해 주세요.")
    private String address;

<<<<<<< Updated upstream
    @NotBlank(message ="휴대전화를 입력해 주세요.")
=======
    @Pattern(regexp = "^(01[1|6|7|8|9|0])-(\\d{3,4})-(\\d{4})$", message = "01x-xxxx-xxxx의 형식으로 작성해주세요")
>>>>>>> Stashed changes
    private String phoneNum;

    @NotBlank(message ="생일을 입력해 주세요.")
    private String birthday;

}
