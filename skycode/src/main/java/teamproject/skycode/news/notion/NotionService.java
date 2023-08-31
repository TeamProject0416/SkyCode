package teamproject.skycode.news.notion;

import org.springframework.beans.factory.annotation.Autowired;
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
        Notion notion = notionForm.toEntity();
        return notionRepository.save(notion);
    }

    public List<Notion> getAllNotions(){
        return notionRepository.findAll();
    }
}
