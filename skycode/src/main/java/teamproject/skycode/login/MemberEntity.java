package teamproject.skycode.login;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import teamproject.skycode.common.BaseEntity;
import teamproject.skycode.constant.Gender;
import teamproject.skycode.constant.Role;
import teamproject.skycode.coupon.Member_CouponEntity;
import teamproject.skycode.myPage.users.MemberEditFormDto;
import teamproject.skycode.review.ReviewEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "member")
public class MemberEntity extends BaseEntity {

    @Id
    @Column(name = "Member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)  // unique 제약 조건 추가
    private String nickName;

    @Column(unique = true)  // unique 제약 조건 추가
    private String email;

    @Column
    private String password;

    @Column
    private String address;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private String phoneNum;

    @Column
    private String birthday;

    private String userImgName; // 미니 이미지 파일명
    private String userOriImgName; // 미니 원본 이미지 파일명
    private String userImgUrl; // 미니 이미지 조회 경로

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ReviewEntity> reviewEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Member_CouponEntity> member_CouponEntityList = new ArrayList<>();

    public static MemberEntity createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        MemberEntity member = new MemberEntity();
        member.setName(memberFormDto.getName());
        member.setNickName(memberFormDto.getNickName());
        member.setEmail(memberFormDto.getEmail());
        member.setGender(memberFormDto.getGender());
        member.setAddress(memberFormDto.getAddress());
        member.setPhoneNum(memberFormDto.getPhoneNum());
        member.setBirthday(memberFormDto.getBirthday());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.ADMIN);
        return member;
    }

    public void updateUser(MemberEditFormDto memberEditFormDto) {

        this.name = memberEditFormDto.getName();
        this.birthday = memberEditFormDto.getBirthday();
        this.phoneNum = memberEditFormDto.getPhoneNum();
        this.email = memberEditFormDto.getEmail();
        this.nickName = memberEditFormDto.getNickName();
        this.gender = memberEditFormDto.getGender();

        this.userImgName = memberEditFormDto.getUserImgName();
        this.userOriImgName = memberEditFormDto.getUserOriImgName();
        this.userImgUrl = memberEditFormDto.getUserImgUrl();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // 예시: 일반 사용자 권한

        // 추가적으로 필요한 권한 정보를 가져오는 로직을 구현해주세요.

        return authorities;
    }
}

