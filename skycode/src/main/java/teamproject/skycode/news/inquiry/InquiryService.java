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
        if (inquiryForm.getId() != null) {
            // Existing inquiry, fetch it first
            Inquiry existingInquiry = getInquiryById(inquiryForm.getId());
            if (existingInquiry != null) {
                // Update existing inquiry with form data
                existingInquiry.setType(inquiryForm.getType());
                existingInquiry.setIsPrivate(inquiryForm.isPrivate());
                existingInquiry.setInquiryTitle(inquiryForm.getInquiryTitle());
                existingInquiry.setInquiryContent(inquiryForm.getInquiryContent());
                return inquiryRepository.save(existingInquiry);
            }
        }

        // New inquiry, create a new entity
        Inquiry newInquiry = inquiryForm.toEntity();
        return inquiryRepository.save(newInquiry);
    }

    public List<Inquiry> getAllInquiries() {
        return inquiryRepository.findAll();
    }

    public Inquiry getInquiryById(Long id) {
        return inquiryRepository.findById(id).orElse(null);
    }

    public long getTotalInquiryCount() {
        return inquiryRepository.count();
    }


    public List<Inquiry> findByInquiryTitleContaining(String searchValue) {
        return inquiryRepository.findByInquiryTitleContaining(searchValue);
    }

    public List<Inquiry> findByInquiryContentContaining(String searchValue) {
        return inquiryRepository.findByInquiryContentContaining(searchValue);
    }

    public List<Inquiry> findByIdContaining(String searchValue) {
        return inquiryRepository.findByIdContaining(searchValue);
    }

}
