package teamproject.skycode.login;


import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import teamproject.skycode.constant.Gender;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Getter
@Setter
public class MemberFormDto {
    private Long id;
    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;
    @NotBlank(message = "닉네임을 입력해 주세요.")
    private String nickName;
    @Email(message = "이메일 형식으로 입력해주세요")
    @Pattern(regexp = "^((\\w+\\-?)+@(\\w+\\.)*\\w+)|(\"[\\w\\.]+\"\\s*<\\s*(\\w+\\-?)+@(\\w+\\.)*\\w+\\s*>)$", message = "skycode@skycode.com 형식으로 작성해주세요")
    private String email;

    @Length(min = 8,max = 16,message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String password;

    private String address;
    @Pattern(regexp = "^(01[1|6|7|8|9|0])-(\\d{3,4})-(\\d{4})$", message = "01x-xxxx-xxxx의 형식으로 작성해주세요")
    private String phoneNum;

    private String birthday;

    private Gender gender;

    private static ModelMapper modelMapper = new ModelMapper();

    private String userImgName; // 미니 이미지 파일명
    private String userOriImgName; // 미니 원본 이미지 파일명
    private String userImgUrl; // 미니 이미지 조회 경로

    public static MemberFormDto of(MemberEntity member) {
        return modelMapper.map(member, MemberFormDto.class);
    }
}
