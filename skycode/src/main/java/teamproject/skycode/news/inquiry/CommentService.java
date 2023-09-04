package teamproject.skycode.news.inquiry;

import org.springframework.stereotype.Service;

@Service
public class CommentService {

    public void submitComment(CommentForm commentForm) {
        // yourInquiryId를 설정


        Long yourInquiryId = 1L; // 실제로 사용 중인 Inquiry의 ID를 여기에 할당

        // 이하 로직에서 yourInquiryId를 사용하여 Inquiry 조회 및 작업 수행
        // ...
    }
}
