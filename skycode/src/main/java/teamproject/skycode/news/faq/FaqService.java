package teamproject.skycode.news.faq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional
public class FaqService {

    private final FaqRepository faqRepository;

    private EntityManager entityManager;

    @Autowired
    public FaqService(FaqRepository faqRepository){
        this.faqRepository = faqRepository;
    }

    @Transactional
    public Faq saveFaq(FaqForm faqForm){
        Faq faq = faqForm.toEntity();
        return faqRepository.save(faq);
    }

    public List<Faq> getAllFaqs(){
        return faqRepository.findAll();
    }


}
