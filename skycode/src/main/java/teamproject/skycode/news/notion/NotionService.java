package teamproject.skycode.news.notion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
//        if (notionForm.getId() != null) {
//            // Existing notion, fetch it first
//            Notion existingNotion = getNotionById(notionForm.getId());
//            if (existingNotion != null) {
//                // Update existing notion with form data
//                existingNotion.setType(notionForm.getType());
//                existingNotion.setNotionTitle(notionForm.getNotionTitle());
//                existingNotion.setNotionContent(notionForm.getNotionContent());
//                return notionRepository.save(existingNotion);
//            }
//        }
        if (notionForm.getType() == null || notionForm.getNotionTitle() == null || notionForm.getNotionContent() == null) {
            throw new IllegalArgumentException("Notion data cannot contain null values.");
        }

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
        return notionRepository.findAllByOrderByCountViewDesc();
    }

    @Transactional
    public void deleteNotion(Long notionId) {
        try {
            Notion notion = notionRepository.findById(notionId).orElse(null);
            if (notion != null){
                notionRepository.delete(notion);
            } else {
                throw new EmptyResultDataAccessException("문의글을 찾을 수 없습니다", 1);
            }
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public Notion findById(Long id) {
        return notionRepository.findById(id).orElse(null);
    }

    public void editNotion(Notion notion) {
        notionRepository.save(notion);
    }

    public Notion saveNotion(Notion notionEntity) {
        return notionRepository.save(notionEntity);
    }
}
