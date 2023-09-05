package teamproject.skycode.news.inquiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.Comparator;
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

    @Transactional
    public void deleteInquiry(Long inquiryId) {
        try {
            // 문의글을 삭제하기 전에 해당 ID의 문의글이 존재하는지 확인합니다.
            Inquiry inquiry = inquiryRepository.findById(inquiryId).orElse(null);
            if (inquiry != null) {
                // 문의글이 존재하면 삭제합니다.
                inquiryRepository.delete(inquiry);
            } else {
                // 해당 ID의 문의글이 없을 경우 예외 처리 또는 메시지를 추가할 수 있습니다.
                throw new EmptyResultDataAccessException("문의글을 찾을 수 없습니다.", 1);
            }
        } catch (EmptyResultDataAccessException e) {
            // 처리할 예외 로직을 추가하세요.
            // 예를 들어, 로그를 남기거나 사용자에게 오류 메시지를 반환할 수 있습니다.
            e.printStackTrace(); // 예외를 로그로 출력하거나 다른 처리를 수행하세요.
        }
    }

    @Transactional
    public Inquiry findById(Long id) {
        return inquiryRepository.findById(id).orElse(null);
    }

    @Transactional
    public void editInquiry(Inquiry inquiry) {
        inquiryRepository.save(inquiry);
    }

    public List<Inquiry> getAllInquiriesSortedByDate() {
        return inquiryRepository.findAllOrderByRegistrationTimeDesc();
    }

    public List<Inquiry> getAllInquiriesSortedByPopularity() {
        return inquiryRepository.findAllByOrderByViewCountDesc();
    }

}
