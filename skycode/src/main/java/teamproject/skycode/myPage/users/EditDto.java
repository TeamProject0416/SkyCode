package teamproject.skycode.myPage.users;

import lombok.Getter;
import lombok.Setter;
import teamproject.skycode.constant.Role;
import teamproject.skycode.login.MemberEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EditDto {

    private Long id;

    private String name;

    @Column(unique = true)  // unique 제약 조건 추가
    private String nickName;

    private String gender;

    private String email;

    private String phoneNum;

    private String birthday;

    private String userImgName; // 미니 이미지 파일명
    private String userOriImgName; // 미니 원본 이미지 파일명
    private String userImgUrl; // 미니 이미지 조회 경로

    public void AllUpdate(MemberEntity userInfo) {

    }
}
