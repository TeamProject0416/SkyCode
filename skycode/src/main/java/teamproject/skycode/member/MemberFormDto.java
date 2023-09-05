package teamproject.skycode.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {

    private Long id;

    private String name;

    private String userId;

    private String password;

    // 비밀번호 확인창
    private String passwordChck;

    private String phone;

    private String birthday;

    private String email;

    private String address;


}
