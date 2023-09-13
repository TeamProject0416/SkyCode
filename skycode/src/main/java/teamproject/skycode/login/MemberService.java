package teamproject.skycode.login;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import teamproject.skycode.common.FileService;
import teamproject.skycode.event.EventEntity;
import teamproject.skycode.myPage.users.EditDto;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final FileService fileService;

    @Value("${imgLocation}")
    private String imgLocation;

    public MemberEntity saveMember(MemberEntity member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(MemberEntity member) {
        MemberEntity findmember = memberRepository.findByEmail(member.getEmail());
        if (findmember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberEntity member = memberRepository.findByEmail(email);
        if(member == null){
            throw new UsernameNotFoundException(email);
        }
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    public Long updateUser(EditDto editDto, MultipartFile userImgFile)  throws Exception{
        // 상품 수정
        MemberEntity member = memberRepository.findByEmail(editDto.getEmail());
        System.err.println(editDto.getEmail());

        // 파일 경로 설정
        String basePath = "/user";

        // 이미지 업로드 처리 및 파일명 생성
        String userImgName = member.getUserImgName();
        String userOriImgName = member.getUserOriImgName();
        String userImgUrl = member.getUserImgUrl();

        if (userImgFile != null && !userImgFile.isEmpty()) {

            // 수정 전 파일 삭제하기
            if(member.getUserImgName() != null) {
                String filePath = imgLocation + basePath + "/" + userImgName;
                File fileToDelete = new File(filePath);
                if (fileToDelete.delete()) {
                    System.out.println("파일이 성공적으로 삭제되었습니다.");
                } else {
                    System.err.println("파일 삭제 중 오류가 발생했습니다.");
                }
            }

            // 파일명 가져오기
            userOriImgName = userImgFile.getOriginalFilename();

            // 파일명 생성
            userImgName = fileService.uploadFile(imgLocation + basePath, userOriImgName,
                    userImgFile.getBytes());

            // 파일 경로 생성
            userImgUrl = "/img/user/" + userImgName;
        }

        editDto.setUserImgName(userImgName);
        editDto.setUserOriImgName(userOriImgName);
        editDto.setUserImgUrl(userImgUrl);

        member.updateUser(editDto);

        return member.getId();
    }
}













