package teamproject.skycode.news.inquiry;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryViewCountRepository extends JpaRepository<InquiryViewCount, Long> {
    InquiryViewCount findByInquiryId(Long inquiryId);
}
