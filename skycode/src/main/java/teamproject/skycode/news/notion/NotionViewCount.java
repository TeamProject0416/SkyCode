package teamproject.skycode.news.notion;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NotionViewCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long notionId;

    private Long count;

    public Long getNotionId() {
        return notionId;
    }

    public void setNotionId(Long inquiryId) {
        this.notionId = inquiryId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
