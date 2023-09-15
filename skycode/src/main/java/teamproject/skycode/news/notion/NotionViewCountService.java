package teamproject.skycode.news.notion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotionViewCountService {


    private final NotionViewCountRepository notionViewCountRepository;

    @Autowired
    public NotionViewCountService(NotionViewCountRepository notionViewCountRepository){
        this.notionViewCountRepository = notionViewCountRepository;
    }

    @Transactional
    public NotionViewCount incrementViewCount(Long notionId){

        NotionViewCount notionViewCount = notionViewCountRepository.findByNotionId(notionId);

        if (notionViewCount == null) {
            notionViewCount = new NotionViewCount();
            notionViewCount.setNotionId(notionId);
            notionViewCount.setCountView(1L);
        } else {
            notionViewCount.setCountView(notionViewCount.getCountView() + 1);
        }
        return notionViewCountRepository.save(notionViewCount);
    }

}
