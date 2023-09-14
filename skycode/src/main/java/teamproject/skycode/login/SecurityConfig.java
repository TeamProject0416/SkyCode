package teamproject.skycode.login;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.net.URLEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/member/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/member/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/member/login?logout=" + URLEncoder.encode("로그아웃 되었습니다","UTF-8"))
                .clearAuthentication(true) /*권한정보 제거*/
        ;

        http.csrf()
                .ignoringAntMatchers("/event/**",
                        "/review/**",
                        "/ticket/**",
                        "/coupon/**",
                        "/comment/**",
                        "/user/**"
                ) //csrf예외처리
        ;

        http.authorizeRequests()
                .mvcMatchers("*").permitAll()
                .mvcMatchers("/css/**", "/js/**", "/img/**", "/mainImages/**","/subImages/**").permitAll() // 전체 공개
                .mvcMatchers("/", "/member/**", "/images/**","/skycode/**", "/SkyCodeProject/**").permitAll() // 전체공개
//                .antMatchers("/user_shopping/orderList").hasAnyAuthority(Role.USER.name(),Role.ADMIN.name())
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest().authenticated()/* 그 외 모든 요청은 인증된 사용자만 접근이 가능하게 처리*/
        ;

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // 사용자 비밀번호를 안전한 방식으로 (암호화) 하기 위한 BCryptPasswordEncoder(); 빈을 생성하고 반환

}

