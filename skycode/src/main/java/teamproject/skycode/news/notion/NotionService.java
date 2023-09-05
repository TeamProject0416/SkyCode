package teamproject.skycode.news.notion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.skycode.news.inquiry.Inquiry;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional
public class NotionService {

    private final NotionRepository notionRepository;

    private EntityManager entityManager;

    @Autowired
    public NotionService(NotionRepository notionRepository){
        this.notionRepository = notionRepository;
    }

    @Transactional
    public Notion saveNotion(NotionForm notionForm){
        if (notionForm.getId() != null) {
            // Existing notion, fetch it first
            Notion existingNotion = getNotionById(notionForm.getId());
            if (existingNotion != null) {
                // Update existing notion with form data
                existingNotion.setType(notionForm.getType());
                existingNotion.setNotionTitle(notionForm.getNotionTitle());
                existingNotion.setNotionContent(notionForm.getNotionContent());
                return notionRepository.save(existingNotion);
            }
        }

        Notion newNotion = notionForm.toEntity();
        return notionRepository.save(newNotion);
    }

    public List<Notion> getAllNotions(){
        return notionRepository.findAll();
    }

    public Notion getNotionById(Long id) {
        return notionRepository.findById(id).orElse(null);
    }

    // 공지글 총갯수 표시
    public long getTotalNotionCount() {
        return notionRepository.count();
    }

    public void createNotion(Notion newNotion) {
        notionRepository.save(newNotion);
    }


    public List<Notion> getAllNotionsSortedByDate() {
        return notionRepository.findAllOrderNotionByRegistrationTimeDesc();
    }
    public List<Notion> getAllNotionsSortedByPopularity() {
        return notionRepository.findAllByOrderByViewCountDesc();
    }

}
