package teamproject.skycode.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import teamproject.skycode.constant.Role;

import java.util.ArrayList;
import java.util.List;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MemberEntity member = memberRepository.findByEmail(username);
        if (member == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        Role role = member.getRole();
        if (role != null) {
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }

        // UserDetails 객체를 생성하여 사용자 정보와 권한을 설정합니다.
        UserDetails userDetails = new User(member.getEmail(), member.getPassword(), authorities);

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .authorities(authorities)
                .build();
    }

    private MemberEntity getMemberByUsername(String username) {
        return memberRepository.findByEmail(username);
    }


}
