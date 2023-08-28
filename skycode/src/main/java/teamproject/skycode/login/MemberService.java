package teamproject.skycode.login;

public class MemberService {

    public Long join(Member member){
        validateDuplicateMember(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        MemberRepository.findByName(member.getName());
    }

}
