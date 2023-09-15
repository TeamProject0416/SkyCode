package teamproject.skycode.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

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
<<<<<<< Updated upstream
=======

    public MemberEditFormDto getMemberDtl(Long memberId) {
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(EntityNotFoundException::new);
        // member 정보를 memberEditFormDto 로 변환합니다
        MemberEditFormDto memberEditFormDto = MemberEditFormDto.of(member);
        return memberEditFormDto;
    }

    public Long updateUser(MemberEditFormDto memberEditFormDto, MultipartFile userImgFile) throws Exception {
        // 유저 정보 수정
        MemberEntity member = memberRepository.findById(memberEditFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        ;

        // 파일 경로 설정
        String basePath = "/user";

        // 폴더 없을시 폴더 생성
        Path directoryPath = Paths.get("/SkyCodeProject/img/user");

        try {
            // 디렉토리 생성
            Files.createDirectories(directoryPath);
            System.out.println(directoryPath + " 디렉토리가 생성되었습니다.");

        } catch (IOException e) {
            System.out.println(directoryPath + " 디렉토리 생성이 실패하였습니다.");
            e.printStackTrace();
        }

        // 이미지 업로드 처리 및 파일명 생성
        String userImgName = member.getUserImgName();
        String userOriImgName = member.getUserOriImgName();
        String userImgUrl = member.getUserImgUrl();

        if (userImgFile != null && !userImgFile.isEmpty()) {
            // 수정 전 파일 삭제하기
            if (member.getUserImgName() != null) {
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

        // DB 업데이트 시간 저장
        LocalDateTime now = LocalDateTime.now();
        member.setUpdateTime(now);

        memberEditFormDto.setUserImgName(userImgName);
        memberEditFormDto.setUserOriImgName(userOriImgName);
        memberEditFormDto.setUserImgUrl(userImgUrl);

        member.updateUser(memberEditFormDto);

        return member.getId();
    }

>>>>>>> Stashed changes
}













