package teamproject.skycode.news.inquiry;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional
//@RequiredArgsConstructor
public class InquiryService {
    private final InquiryRepository inquiryRepository;
    private EntityManager entityManager;


    @Autowired
    public InquiryService(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @Transactional
    public Inquiry saveInquiry(InquiryForm inquiryForm) {
        Inquiry inquiry = inquiryForm.toEntity();
        return inquiryRepository.save(inquiry);
    }

    public List<Inquiry> getAllInquiries() {
        return inquiryRepository.findAll();
    }

}
