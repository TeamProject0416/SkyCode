package teamproject.skycode.news.inquiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class InquiryViewCountService {

    private final InquiryViewCountRepository inquiryViewCountRepository;

    @Autowired
    public InquiryViewCountService(InquiryViewCountRepository inquiryViewCountRepository) {
        this.inquiryViewCountRepository = inquiryViewCountRepository;
    }

    @Transactional
    public InquiryViewCount incrementViewCount(Long inquiryId) {


        InquiryViewCount inquiryViewCount = inquiryViewCountRepository.findByInquiryId(inquiryId);

        if (inquiryViewCount == null) {
            inquiryViewCount = new InquiryViewCount();
            inquiryViewCount.setInquiryId(inquiryId);
            inquiryViewCount.setCount(1L);
        } else {
            inquiryViewCount.setCount(inquiryViewCount.getCount() + 1);
        }

        return inquiryViewCountRepository.save(inquiryViewCount);
    }
}
