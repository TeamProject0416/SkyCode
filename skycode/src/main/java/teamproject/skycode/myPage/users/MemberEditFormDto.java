package teamproject.skycode.myPage.users;


import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import teamproject.skycode.constant.Gender;
import teamproject.skycode.login.MemberEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Getter
@Setter
public class MemberEditFormDto {
    private Long id;

    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;

    @NotBlank(message = "닉네임을 입력해 주세요.")
    private String nickName;

    @NotEmpty(message = "이메일은 필수 입력 값입니다")
    @Email(message = "이메일 형식으로 입력해주세요")
    private String email;

    private String phoneNum;

    private String address;
    private String birthday;

    private Gender gender;

    private static ModelMapper modelMapper = new ModelMapper();

    private String userImgName; // 미니 이미지 파일명
    private String userOriImgName; // 미니 원본 이미지 파일명
    private String userImgUrl; // 미니 이미지 조회 경로

    public static MemberEditFormDto of(MemberEntity member) {
        return modelMapper.map(member, MemberEditFormDto.class);
    }
}
